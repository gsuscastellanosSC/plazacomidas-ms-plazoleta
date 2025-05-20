package com.plazacomidas.plazoleta.domain.repository;

import com.plazacomidas.plazoleta.domain.model.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
}
