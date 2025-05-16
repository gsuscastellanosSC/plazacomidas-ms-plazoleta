package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.validation.RestaurantValidator;
import com.plazacomidas.plazoleta.application.port.in.CreateRestaurantUseCasePort;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRestaurantUseCase implements CreateRestaurantUseCasePort {

    private final RestaurantValidator restaurantValidator;
    private final RestaurantRepository restaurantRepository;
    private final ValidateRestaurantOwnerService validateRestaurantOwnerService;

    @Override
    public Restaurant execute(Restaurant restaurant) {
        restaurantValidator.validateCreateRestaurant(restaurant);
        validateRestaurantOwnerService.validate(restaurant.getIdPropietario());
        return restaurantRepository.save(restaurant);
    }
}
