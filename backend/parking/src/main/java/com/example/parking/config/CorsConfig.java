package com.example.parking.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 👉 Cambia esto por tu frontend real
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));

        // Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Headers permitidos
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Exponer headers personalizados (opcional, ej. Authorization)
        config.setExposedHeaders(List.of("Authorization"));

        // Si necesitas enviar cookies / credenciales
        config.setAllowCredentials(true);

        // Tiempo de cache del preflight (OPTIONS)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
