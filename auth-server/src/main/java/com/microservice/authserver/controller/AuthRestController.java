package com.microservice.authserver.controller;

import com.google.gson.Gson;
import com.microservice.authserver.entity.Activity;
import com.microservice.authserver.entity.Result;
import com.microservice.authserver.entity.Token;
import com.microservice.authserver.entity.User;
import com.microservice.authserver.service.TokenService;
import com.microservice.authserver.service.UserService;
import com.microservice.authserver.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<?> login(@RequestParam("username") String _userName, @RequestParam("password") String _password) {
        log.info("Request POST /auth/login");
        User user = userService.isAuthenticated(_userName, _password);
        if (!ObjectUtils.isEmpty(user)) {
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

            Result result = Result.builder()
                    .userId(user.getId())
                    .activities(APIs)
                    .build();
            // save token to redis
            tokenService.saveToken(token.getToken(), result);

            log.info("Log in successful");
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        log.info("Log in fail !!");
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }



}
