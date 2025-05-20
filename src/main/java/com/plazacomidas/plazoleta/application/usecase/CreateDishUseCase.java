package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.validation.DishOwnerValidator;
import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateDishUseCase {

    private final DishRepository dishRepository;
    private final DishOwnerValidator dishOwnerValidator;

    public Dish execute(Dish dish, Long userId) {
        dishOwnerValidator.validateOwnership(userId, dish.getRestauranteId());
        return dishRepository.save(dish);
    }
}
