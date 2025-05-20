package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;
import com.plazacomidas.plazoleta.application.port.in.UpdateDishUseCasePort;
import com.plazacomidas.plazoleta.application.validation.DishFieldValidator;
import com.plazacomidas.plazoleta.domain.exception.UnauthorizedDishModificationException;
import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDishUseCase implements UpdateDishUseCasePort {

    private final DishRepository dishPersistencePort;
    private final RestaurantRepository restaurantPersistencePort;

    @Override
    public void execute(Long dishId, Long userId, UpdateDishRequestDto dto) {
        Dish dish = dishPersistencePort.findById(dishId)
                .orElseThrow(() -> new IllegalArgumentException("El plato no existe."));

        Restaurant restaurant = restaurantPersistencePort.findById(dish.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("El restaurante no existe."));

        if (!restaurant.getIdPropietario().equals(userId)) {
            throw new UnauthorizedDishModificationException();
        }

        DishFieldValidator.PRICE.validate(dto.getPrice());
        DishFieldValidator.DESCRIPTION.validate(dto.getDescription());

        dish.setPrecio(dto.getPrice());
        dish.setDescripcion(dto.getDescription());

        dishPersistencePort.update(dish);
    }
}
