package com.plazacomidas.plazoleta.domain.repository;

import com.plazacomidas.plazoleta.domain.model.Dish;

import java.util.Optional;

public interface DishRepository {
    Dish save(Dish dish);
    void update(Dish dish);
    Optional<Dish> findById(Long id);
}
