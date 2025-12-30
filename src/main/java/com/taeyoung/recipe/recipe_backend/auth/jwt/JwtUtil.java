package com.taeyoung.recipe.recipe_backend.auth.jwt;

import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtil {

//    static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    static final SecretKey key = Keys.hmacShaKeyFor("your-secret-key-your-secret-key-32byte".getBytes());

    // JWT 만들어주는 함수
    public static String createToken(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
//        var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority())
//                .collect(Collectors.joining(","));
        var authorities = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();

        String jwt = Jwts.builder()
                .claim("id", user.id)
                .claim("username", user.getUsername())
                .claim("provider", user.getProvider().name())
                .claim("authorities", authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000000)) //유효기간 1000초
                .signWith(key)
                .compact();
        return jwt;
    }

    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }
}
