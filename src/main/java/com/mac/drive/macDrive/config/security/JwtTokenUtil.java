package com.mac.drive.macDrive.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Utility Class
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.expiration}")
    private Integer expiration;
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generate token based on username
     *
     * @param userDetails
     * @return
     */
    public String generatorToken(UserDetails userDetails) {
        Map<String, Object> map = new HashMap<>();
        map.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        map.put(CLAIM_KEY_CREATED, new Date());
        return generatorToken(map);
    }

    /**
     * Get username from token
     *
     * @return
     */
    public String getUserNameFormToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * Validate if the token is valid
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFormToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Check if the token can be refreshed
     *
     * @param token
     * @return
     */
    public boolean isRefreshToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Refresh Token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generatorToken(claims);
    }

    /**
     * Verify if the token has expired
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date exp = claims.getExpiration();
        return exp.before(new Date());
    }

    /**
     * Get payload from token
     *
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Generate Token based on payload
     *
     * @param map
     * @return
     */
    private String generatorToken(Map<String, Object> map) {
        return Jwts.builder()
                .setClaims(map)
                .setExpiration(generatorExpiration())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Generate expiration date
     *
     * @return
     */
    private Date generatorExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
