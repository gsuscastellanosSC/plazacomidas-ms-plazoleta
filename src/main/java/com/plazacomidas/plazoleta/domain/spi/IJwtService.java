package com.plazacomidas.plazoleta.domain.spi;


import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     * @param token el JWT
     * @return el nombre de usuario (correo electrónico o username)
     */
    String extractUsername(String token);

    /**
     * Verifica si el token es válido y no ha expirado.
     * @param token el JWT
     * @param expectedUsername el usuario esperado (para comparación)
     * @return true si el token es válido
     */
    boolean isTokenValid(String token, String expectedUsername);

    /**
     * Extrae el rol del token JWT, si está presente.
     * @param token el JWT
     * @return el nombre del rol como String
     */
    String extractRole(String token);
}
