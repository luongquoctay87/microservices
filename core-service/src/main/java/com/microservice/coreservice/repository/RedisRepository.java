package com.microservice.coreservice.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class RedisRepository {

    final String REDIS_PREFIX = "TOKEN";

    @Autowired
    private RedisTemplate redisTemplate;

    public String findResdisData(String token) {
        Objects.requireNonNull(token);
        return (String) redisTemplate.opsForHash().get(REDIS_PREFIX, token);
    }


}