package com.microservice.authserver.service;

import com.microservice.authserver.entity.Result;
import com.microservice.authserver.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public void saveToken(String token, Result result) {
        tokenRepository.saveToken(token, result);
    }
}
