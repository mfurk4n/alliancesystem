package com.alliancesystem.alliancesystem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtTokenUtils {

    @Value("${social-app.app.secret}")
    private String APP_SECRET;

    @Value("${social-app.expires.in}")
    private long EXPIRES_IN;

    public String generateJwtToken(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenByUserId(String userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build().parseClaimsJws(token).getBody();

        return claimsTFunction.apply(claims);
    }

    String getUserIdFromJwt(String token) {
        return exportToken(token, Claims::getSubject);
    }

    boolean validateToken(String token, CustomUserDetails customUserDetails) {
        try {
            final String userId = getUserIdFromJwt(token);
            return (userId.equals(customUserDetails.getId()) && !exportToken(token, Claims::getExpiration).before(new Date()));
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(APP_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
