package com.ticketresolve.ticketresolve.configuration.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    // Método privado para obtener la clave de firma
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // aquí van los roles
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar el token de acceso
    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("Token invalido, error: ".concat(e.getMessage()));
            return false;
        }
    }

    // Obtener el username desde el token
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Método genérico para obtener claims
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraer todos los claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // usar el método
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", List.class);
    }

}