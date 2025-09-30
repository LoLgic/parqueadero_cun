package com.example.parking.security;


import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                DecodedJWT decodedJWT = tokenService.validateToken(token);

                String email = decodedJWT.getSubject();
                String rol = decodedJWT.getClaim("rol").asString();

                var auth = new UsernamePasswordAuthenticationToken(
                        email, // principal
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + rol)) // authorities
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception ex) {
                // Token inválido → no seteamos nada en el contexto
                System.out.println("JWT inválido: " + ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
