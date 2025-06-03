package com.plazacomidas.plazoleta.domain.repository;

import com.plazacomidas.plazoleta.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface DishRepository {
    Dish save(Dish dish);
    void update(Dish dish);
    Optional<Dish> findById(Long id);
    Page<Dish> findByRestaurantIdAndCategoria(Long restauranteId, String categoria, Pageable pageable);
}
