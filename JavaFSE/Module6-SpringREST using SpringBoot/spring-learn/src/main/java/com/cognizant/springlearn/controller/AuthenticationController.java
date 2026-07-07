package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.security.JwtAuthorizationFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Authentication Controller — JWT hands-on.
 *
 * GET /authenticate  (requires HTTP Basic credentials)
 * Reads the Authorization header, decodes the username, generates a JWT and returns it.
 *
 * Usage:
 *   curl -s -u user:pwd http://localhost:8083/authenticate
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(
            @RequestHeader("Authorization") String authHeader) {

        LOGGER.info("START");
        LOGGER.debug("Authorization header: {}", authHeader);

        String user = getUser(authHeader);
        LOGGER.debug("Authenticated user: {}", user);

        String token = generateJwt(user);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        LOGGER.info("END");
        return response;
    }

    /**
     * Decodes the Base64-encoded credentials from the Authorization header
     * and returns the username portion (everything before the colon).
     */
    private String getUser(String authHeader) {
        LOGGER.debug("START getUser");

        // authHeader format: "Basic <base64(user:pwd)>"
        String encodedCredentials = authHeader.substring("Basic ".length());
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String credentials = new String(decodedBytes);

        // "user:pwd" → take text up to the colon
        String user = credentials.substring(0, credentials.indexOf(':'));
        LOGGER.debug("Decoded user: {}", user);

        return user;
    }

    /**
     * Generates a signed JWT for the given username.
     * Token is valid for 20 minutes.
     */
    @SuppressWarnings("deprecation")
    private String generateJwt(String user) {
        LOGGER.debug("START generateJwt for user: {}", user);

        Key key = Keys.hmacShaKeyFor(
                JwtAuthorizationFilter.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1_200_000)) // 20 minutes
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        LOGGER.debug("Generated token: {}", token);
        return token;
    }
}
