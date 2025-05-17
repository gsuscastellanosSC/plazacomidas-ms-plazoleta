package com.plazacomidas.plazoleta.application.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.domain.exception.InvalidRestaurantException;
import org.junit.jupiter.api.Test;

class RestaurantValidatorTest {

    @Test
    void validateValidName() {
        assertDoesNotThrow(() -> RestaurantFieldValidator.NAME.validate("Mi Restaurante 123"));
    }

    @Test
    void validateInvalidNameOnlyNumbers() {
        InvalidRestaurantException ex = assertThrows(
                InvalidRestaurantException.class,
                () -> RestaurantFieldValidator.NAME.validate("123456"));
        assertEquals(RestaurantConstants.MSG_NAME_ONLY_NUMBERS, ex.getMessage());
    }

    @Test
    void validateValidNit() {
        assertDoesNotThrow(() -> RestaurantFieldValidator.NIT.validate("987654321"));
    }

    @Test
    void validateInvalidNitNonNumeric() {
        InvalidRestaurantException ex = assertThrows(
                InvalidRestaurantException.class,
                () -> RestaurantFieldValidator.NIT.validate("ABC123"));
        assertEquals(RestaurantConstants.MSG_NIT_NUMERIC, ex.getMessage());
    }

    @Test
    void validateValidPhone() {
        assertDoesNotThrow(() -> RestaurantFieldValidator.PHONE.validate("+573001112233"));
    }

    @Test
    void validateInvalidPhoneTooLong() {
        InvalidRestaurantException ex = assertThrows(
                InvalidRestaurantException.class,
                () -> RestaurantFieldValidator.PHONE.validate("+5730011122334455"));
        assertEquals(RestaurantConstants.MSG_PHONE_FORMAT, ex.getMessage());
    }

    @Test
    void validateInvalidPhoneWrongCharacters() {
        InvalidRestaurantException ex = assertThrows(
                InvalidRestaurantException.class,
                () -> RestaurantFieldValidator.PHONE.validate("ABC123"));
        assertEquals(RestaurantConstants.MSG_PHONE_FORMAT, ex.getMessage());
    }

    @Test
    void validateValidAddress() {
        assertDoesNotThrow(() -> RestaurantFieldValidator.ADDRESS.validate("Calle 123"));
    }

    @Test
    void validateInvalidAddressEmpty() {
        InvalidRestaurantException ex = assertThrows(
                InvalidRestaurantException.class,
                () -> RestaurantFieldValidator.ADDRESS.validate("   "));
        assertEquals(RestaurantConstants.MSG_INVALID_ADDRESS, ex.getMessage());
    }

    @Test
    void validateValidLogoUrl() {
        assertDoesNotThrow(() -> RestaurantFieldValidator.LOGO_URL.validate("https://logo.com/img.png"));
    }

    @Test
    void validateInvalidLogoUrlNull() {
        InvalidRestaurantException ex = assertThrows(
                InvalidRestaurantException.class,
                () -> RestaurantFieldValidator.LOGO_URL.validate(null));
        assertEquals(RestaurantConstants.MSG_INVALID_LOGO_URL, ex.getMessage());
    }
}
