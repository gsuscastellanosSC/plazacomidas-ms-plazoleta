package com.plazacomidas.plazoleta.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiError {

    BUSINESS_RULE_VIOLATION(
            "BUSINESS_RULE_VIOLATION",
            HttpStatus.BAD_REQUEST,
            "Violación de una regla de negocio. Por favor, revise los datos.",
            "BUSINESS_RULE_VIOLATION | Path: {} | Timestamp: {} | Detalles: {}"
    ),

    INVALID_CREDENTIALS(
            "INVALID_CREDENTIALS",
            HttpStatus.UNAUTHORIZED,
            "Credenciales inválidas",
            "{} | Path: {} | Timestamp: {}"
    ),
    INVALID_OWNER("INVALID_OWNER", HttpStatus.BAD_REQUEST, "El usuario no tiene el rol de propietario.", "INVALID_OWNER | Path: {} | Timestamp: {} | {}"),
    INVALID_REQUEST("INVALID_REQUEST", HttpStatus.BAD_REQUEST, "Solicitud inválida: estructura incorrecta, campos faltantes o datos inválidos", "INVALID_REQUEST | Path: {} | Timestamp: {} | {}"),
    INVALID_RESTAURANT("INVALID_RESTAURANT", HttpStatus.BAD_REQUEST, "Datos inválidos para la creación del restaurante.", "INVALID_RESTAURANT | Path: {} | Timestamp: {} | {}"),
    INVALID_NIT("INVALID_NIT", HttpStatus.BAD_REQUEST, "El NIT del restaurante debe ser numérico.", "INVALID_NIT | Path: {} | Timestamp: {} | {}"),
    INVALID_PHONE("INVALID_PHONE", HttpStatus.BAD_REQUEST, "El teléfono del restaurante no tiene un formato válido.", "INVALID_PHONE | Path: {} | Timestamp: {} | {}"),
    INVALID_NAME("INVALID_NAME", HttpStatus.BAD_REQUEST, "El nombre del restaurante no puede estar compuesto solo por números.", "INVALID_NAME | Path: {} | Timestamp: {} | {}"),
    INVALID_ADDRESS("INVALID_ADDRESS", HttpStatus.BAD_REQUEST, "La dirección no debe estar vacía.", "INVALID_ADDRESS | Path: {} | Timestamp: {} | {}"),
    INVALID_LOGO_URL("INVALID_LOGO_URL", HttpStatus.BAD_REQUEST, "La URL del logo no debe estar vacía.", "INVALID_LOGO_URL | Path: {} | Timestamp: {} | {}"),
    MISSING_FIELDS("MISSING_FIELDS", HttpStatus.BAD_REQUEST, "Faltan campos obligatorios para la creación del restaurante.", "MISSING_FIELDS | Path: {} | Timestamp: {} | {}"),
    SYSTEM_ERROR("SYSTEM_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", "SYSTEM_ERROR | Path: {} | Timestamp: {} | {}"),
    INVALID_RESTAURANT_FIELD("INVALID_RESTAURANT_FIELD", HttpStatus.BAD_REQUEST, "Uno o más campos del restaurante no son válidos.", "INVALID_RESTAURANT_FIELD | Path: {} | Timestamp: {} | {}");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String description;
    private final String logFormat;

    ApiError(String errorCode, HttpStatus httpStatus, String description, String logFormat) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.description = description;
        this.logFormat = logFormat;
    }
}
