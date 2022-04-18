package com.microservice.cloudgateway.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.microservice.cloudgateway.model.ResultClass;
import com.microservice.cloudgateway.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public boolean isAuthorized(String token, String method, String url) {

        String redisResult = tokenRepository.findActivities(token);
        ObjectMapper objectMapper = new ObjectMapper();

        ResultClass resultClass = null;
        try {
             resultClass = objectMapper.readValue(redisResult, ResultClass.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       String activities = resultClass.getActivitiesJson();
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
