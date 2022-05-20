package com.microservice.cloudgateway.filter;

import com.google.gson.Gson;
import com.microservice.cloudgateway.exception.JwtTokenMalformedException;
import com.microservice.cloudgateway.exception.JwtTokenMissingException;
import com.microservice.cloudgateway.model.ErrorResponse;
import com.microservice.cloudgateway.service.TokenService;
import com.microservice.cloudgateway.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
public class JwtAuthenticationFilter implements GatewayFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

        final List<String> apiEndpoints = Arrays.asList("/register", "/login");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(request)) {
            ServerHttpResponse response = exchange.getResponse();
            String url = request.getPath().toString();

            if (!request.getHeaders().containsKey("Authorization")) {
                return error(response, HttpStatus.UNAUTHORIZED, "Access denied", url);
            }

            // get token and remove character "Bearer "
            final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);

            // validate token and authenticate
            try {
                jwtUtil.validateToken(token);
                Claims claims = jwtUtil.getClaims(token);
                exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
            } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                log.info("JWT format invalid !!");
                return error(response, HttpStatus.UNAUTHORIZED, "Unauthenticated", url);
            }

            // compare with redis and authorize
//            String method = request.getMethod().toString();
//            if (!tokenService.isAuthorized(token, method, url)) {
//                return error(response, HttpStatus.FORBIDDEN, "Access denied", url);
//            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> error(ServerHttpResponse response, HttpStatus status, String msg, String url) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(new Date());
        error.setPath(url);
        error.setStatus(status.value());
        error.setError(msg);
        byte[] bytes = new Gson().toJson(error).getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(status);

        return response.writeWith(Mono.just(buffer));
    }
}
