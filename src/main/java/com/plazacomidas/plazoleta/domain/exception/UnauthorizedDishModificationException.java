package com.plazacomidas.plazoleta.domain.exception;

public class UnauthorizedDishModificationException extends RuntimeException {
    public UnauthorizedDishModificationException() {
        super("No está autorizado para modificar este plato.");
    }
}
