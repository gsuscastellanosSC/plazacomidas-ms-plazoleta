package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.domain.model.Dish;
import org.springframework.data.domain.Page;

public interface GetDishesUseCasePort {
    Page<Dish> execute(Long restauranteId, String categoria, int page, int size);
}
