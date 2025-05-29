package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.port.in.ChangeDishAvailabilityUseCasePort;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;

@Service
@RequiredArgsConstructor
public class ChangeDishAvailabilityUseCase implements ChangeDishAvailabilityUseCasePort {

    private final DishRepository dishPersistencePort;
    private final RestaurantRepository restaurantPersistencePort;

    @Override
    public void execute(Long dishId, Long userId, boolean active) {
        Dish dish = dishPersistencePort.findById(dishId)
                .orElseThrow(() -> new BusinessException("El plato no existe."));

        Restaurant restaurant = restaurantPersistencePort.findById(dish.getRestauranteId())
                .orElseThrow(InvalidCredentialsException::new);

        if (!restaurant.getIdPropietario().equals(userId)) {
            throw new InvalidCredentialsException();
        }

        dish.setActivo(active);
        dishPersistencePort.update(dish);
    }
}
