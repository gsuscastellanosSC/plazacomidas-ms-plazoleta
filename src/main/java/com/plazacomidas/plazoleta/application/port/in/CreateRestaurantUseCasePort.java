package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.domain.model.Restaurant;

public interface CreateRestaurantUseCasePort {
    Restaurant execute(Restaurant restaurant);
}
