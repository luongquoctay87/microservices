package com.microservice.authserver.controller;

import com.google.gson.Gson;
import com.microservice.authserver.entity.Activity;
import com.microservice.authserver.entity.Token;
import com.microservice.authserver.response.ApiResponse;
import com.microservice.authserver.response.ErrorResponse;
import com.microservice.authserver.service.TokenService;
import com.microservice.authserver.service.UserService;
import com.microservice.authserver.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class AuthRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth/login")
    public ApiResponse login(@RequestParam("username") String _userName, @RequestParam("password") String _password) {
        log.info("Request POST /auth/login");
        if (userService.isAuthenticated(_userName, _password)) {
            String roles = userService.findAllRolesByUsername(_userName).stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            Token token = jwtUtil.generateToken(_userName);
            token.setRoles(roles);

            List<String> APIs = new ArrayList<>();
            List<Activity> activities = userService.findAllActivitiesByRoles(roles);
            activities.forEach(acc -> {
                APIs.add(String.format(String.format("%s %s",acc.getMethod().toUpperCase(), acc.getUrl())));
            });

            // save token to redis
            tokenService.saveToken(token.getToken(), APIs);

            log.info("Log in successful");
            return new ApiResponse(HttpStatus.OK.value(), "User is authenticated", token);
        }
        log.info("Log in fail !!");
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password");
    }
}
