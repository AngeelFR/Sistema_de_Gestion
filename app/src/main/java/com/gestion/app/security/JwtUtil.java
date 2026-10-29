package com.gestion.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // token
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tiempo de expiración (ej. 24 horas en milisegundos)
    private final long expirationTime = 86400000;

    // token a partir del username
    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }


    public String generateToken(String username) {
        return generarToken(username);
    }

    // Extraer el username del token
    public String extraerUsername(String token) {
        return obtenerClaims(token).getSubject();
    }

    // Validar si el token es correcto y no ha expirado
    public boolean validarToken(String token, String username) {
        final String usernameToken = extraerUsername(token);
        return (usernameToken.equals(username) && !esTokenExpirado(token));
    }

    private boolean esTokenExpirado(String token) {
        return obtenerClaims(token).getExpiration().before(new Date());
    }

    private Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}