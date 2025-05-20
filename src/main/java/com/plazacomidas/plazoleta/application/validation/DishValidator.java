package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.adapters.in.web.dto.DishRequestDto;
import com.plazacomidas.plazoleta.common.DishConstants;
import com.plazacomidas.plazoleta.domain.exception.InvalidDishException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishValidator {

    public void validateCreateDish(DishRequestDto dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new InvalidDishException(DishConstants.MSG_REQUIRED_NAME);
        }

        if (dto.getPrecio() == null) {
            throw new InvalidDishException(DishConstants.MSG_REQUIRED_PRICE);
        }

        if (dto.getPrecio() <= 0) {
            throw new InvalidDishException(DishConstants.MSG_INVALID_PRICE);
        }

        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new InvalidDishException(DishConstants.MSG_REQUIRED_DESCRIPTION);
        }

        if (dto.getUrlImagen() == null || dto.getUrlImagen().isBlank()) {
            throw new InvalidDishException(DishConstants.MSG_REQUIRED_IMAGE_URL);
        }

        if (dto.getIdCategoria() == null) {
            throw new InvalidDishException(DishConstants.MSG_REQUIRED_CATEGORY);
        }

        if (dto.getRestauranteId() == null) {
            throw new InvalidDishException(DishConstants.MSG_REQUIRED_RESTAURANT);
        }
    }
}
