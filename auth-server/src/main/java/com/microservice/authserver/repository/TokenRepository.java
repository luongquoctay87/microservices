package com.microservice.authserver.repository;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
public class TokenRepository {

    final String REDIS_PREFIX = "TOKEN";

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    @Autowired
    private RedisTemplate redisTemplate;

    public void saveToken(String token, List<String> APIs) {
        Objects.requireNonNull(token);
        Gson gson = new Gson();
        String activities = gson.toJson(APIs);
        redisTemplate.opsForHash().put(REDIS_PREFIX, token, activities);
        redisTemplate.expire(token, tokenValidity, TimeUnit.MINUTES);
    }
}
