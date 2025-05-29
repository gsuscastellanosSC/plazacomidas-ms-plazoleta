package com.plazacomidas.plazoleta.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestaurantException extends RuntimeException {
    public RestaurantException(String message) {
        super(message);
    }
}
