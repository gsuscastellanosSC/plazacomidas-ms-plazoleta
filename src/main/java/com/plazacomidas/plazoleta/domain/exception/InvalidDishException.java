package com.plazacomidas.plazoleta.domain.exception;

public class InvalidDishException extends RuntimeException {
    public InvalidDishException(String message) {
        super(message);
    }
}
