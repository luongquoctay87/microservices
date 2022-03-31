package com.microservice.authserver.utils;

import com.microservice.authserver.entity.Token;
import com.microservice.authserver.exception.JwtTokenMalformedException;
import com.microservice.authserver.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.token.secretKey}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            log.info(e.getMessage() + " => " + e);
        }
        return null;
    }

    public Token generateToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + (tokenValidity * (60 * 1000));
        Date exp = new Date(expMillis);
        String encrypt = Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return Token.builder()
                .token(encrypt)
                .username(username)
                .currentDate(nowMillis)
                .expireTime(expMillis)
                .build();
    }
}
