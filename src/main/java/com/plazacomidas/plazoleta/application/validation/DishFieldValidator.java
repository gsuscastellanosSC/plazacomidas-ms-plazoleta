package com.plazacomidas.plazoleta.application.validation;

import java.util.function.Predicate;

public enum DishFieldValidator {

    PRICE(value -> value instanceof Integer && (Integer) value > 0,
            "El precio debe ser mayor a cero."),
    DESCRIPTION(value -> value instanceof String && !((String) value).trim().isEmpty(),
            "La descripción no debe estar vacía.");
    private final Predicate<Object> predicate;
    private final String errorMessage;

    DishFieldValidator(Predicate<Object> predicate, String errorMessage) {
        this.predicate = predicate;
        this.errorMessage = errorMessage;
    }

    public void validate(Object value) {
        if (!predicate.test(value)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
