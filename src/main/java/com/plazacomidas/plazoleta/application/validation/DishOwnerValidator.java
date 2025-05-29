package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.domain.exception.RestaurantException;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DishOwnerValidator {

    private final RestaurantRepository restaurantRepository;

    public void validateOwnership(Long userId, Long restauranteId) {
        Restaurant restaurante = restaurantRepository.findById(restauranteId)
                .orElseThrow(() -> new RestaurantException("Restaurante no encontrado"));

        if (!Objects.equals(restaurante.getIdPropietario(), userId)) {
            throw new RestaurantException("No es el propietario del restaurante");
        }
    }
}
