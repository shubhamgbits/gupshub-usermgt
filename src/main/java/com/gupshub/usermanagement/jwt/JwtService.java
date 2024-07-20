package com.gupshub.usermanagement.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used for creation of JWT tokens
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    public String generateToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(Date.from(Instant.now(Clock.systemUTC())))
                .expiration(Date.from(Instant.now(Clock.systemUTC()).plusSeconds(1800)))
                .signWith(getSignedKey())
                .compact();
    }

    private SecretKey getSignedKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String jwt){
        Claims claim = getClaims(jwt);

        return claim.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);

        return claims.getExpiration().after(Date.from(Instant.now(Clock.systemUTC())));
    }
}
