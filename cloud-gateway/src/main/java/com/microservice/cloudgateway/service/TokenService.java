package com.microservice.cloudgateway.service;

import com.google.gson.Gson;
import com.microservice.cloudgateway.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public boolean isAuthorized(String token, String method, String url) {
        String activities = tokenRepository.findActivities(token);
        if (!StringUtils.hasLength(activities)) {
            log.info("JWT doesn't exists in Redis Server");
            return false;
        }

        String arr[] = new Gson().fromJson(activities, String[].class);
        String api = String.format("%s %s", method, url);
        
        for (String value : arr) {
            value = value.replace("/{id}", "/(.*?)");
            try {
                if (api.matches(value)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
