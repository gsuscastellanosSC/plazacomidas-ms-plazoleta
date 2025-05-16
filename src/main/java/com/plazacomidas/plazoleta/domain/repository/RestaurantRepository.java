package com.plazacomidas.plazoleta.domain.repository;

import com.plazacomidas.plazoleta.domain.model.Restaurant;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
}
