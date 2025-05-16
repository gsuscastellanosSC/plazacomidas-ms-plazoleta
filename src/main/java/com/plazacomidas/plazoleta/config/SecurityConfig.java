package com.plazacomidas.plazoleta.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para desarrollo
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite TODO
                )
                .headers(headers -> headers
                        .frameOptions().disable() // Permite iframes (requerido por H2)
                );

        return http.build();
    }
}
