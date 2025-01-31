package com.hms2.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {


    @Value("${jwt.algorithm.key}")
    private String algorithmkey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private String expiryString;

    private Algorithm algorithm;

    private long expiry; // Store the expiry as long for milliseconds

    @PostConstruct
    public void postContruct() throws UnsupportedEncodingException {
        algorithm=Algorithm.HMAC256(algorithmkey);
        // Convert expiryString to long
        try {
            expiry = Long.parseLong(expiryString); // Convert expiry to milliseconds
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid expiry duration in application.properties: " + expiryString, e);
        }
    }

    public String generateToken(String username){
        return JWT.create().withClaim("name", username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token){
        DecodedJWT decoded = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decoded.getClaim("name").asString();
    }


}
