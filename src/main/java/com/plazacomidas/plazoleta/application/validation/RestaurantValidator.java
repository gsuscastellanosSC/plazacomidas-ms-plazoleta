package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantValidator {

    public void validateCreateRestaurant(Restaurant dto) {
        RestaurantFieldValidator.NAME.validate(dto.getNombre());
        RestaurantFieldValidator.NIT.validate(dto.getNit());
        RestaurantFieldValidator.PHONE.validate(dto.getTelefono());
        RestaurantFieldValidator.ADDRESS.validate(dto.getDireccion());
        RestaurantFieldValidator.LOGO_URL.validate(dto.getUrlLogo());
    }
}
