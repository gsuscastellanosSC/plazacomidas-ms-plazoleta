package com.plazacomidas.plazoleta.application.validation;


import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.domain.exception.InvalidRestaurantException;

import java.util.function.Predicate;

public enum RestaurantFieldValidator {

    NAME(value -> value != null && !value.isBlank() && !value.matches("^\\d+$"),
            RestaurantConstants.MSG_NAME_ONLY_NUMBERS),

    NIT(value -> value != null && value.matches("^\\d+$"),
            RestaurantConstants.MSG_NIT_NUMERIC),

    PHONE(value -> value != null && value.matches("^\\+?\\d{1,13}$"),
            RestaurantConstants.MSG_PHONE_FORMAT),

    ADDRESS(value -> value != null && !value.isBlank(),
            RestaurantConstants.MSG_INVALID_ADDRESS),

    LOGO_URL(value -> value != null && !value.isBlank(),
            RestaurantConstants.MSG_INVALID_LOGO_URL);

    private final Predicate<String> validator;
    private final String errorMessage;

    RestaurantFieldValidator(Predicate<String> validator, String errorMessage) {
        this.validator = validator;
        this.errorMessage = errorMessage;
    }

    public void validate(String value) {
        if (!validator.test(value)) {
            throw new InvalidRestaurantException(errorMessage);
        }
    }
}
