package com.example.parking.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.parking.domain.model.usuario.Usuario;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    // Generar token con id, email y rol
    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("parking-api")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getIdUsuario())
                    .withClaim("rol", usuario.getRol().name()) // guardamos rol
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    // Validar y obtener claims del token
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); 
            return JWT.require(algorithm)
                    .withIssuer("parking-api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido o expirado", exception);
        }
    }

    private Date generarFechaExpiracion() {
        Instant exp = LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-05:00")); // ajusta a tu zona horaria
        return Date.from(exp);
    }
}
