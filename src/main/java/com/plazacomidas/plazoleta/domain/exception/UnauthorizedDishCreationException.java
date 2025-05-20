package com.plazacomidas.plazoleta.domain.exception;

public class UnauthorizedDishCreationException extends RuntimeException {
    public UnauthorizedDishCreationException(String message) {
        super(message);
    }
}
