package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.port.in.GetDishesUseCasePort;
import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDishesUseCase implements GetDishesUseCasePort {

    private final DishRepository dishRepository;

    @Override
    public Page<Dish> execute(Long restauranteId, String categoria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return dishRepository.findByRestaurantIdAndCategoria(restauranteId, categoria, pageable);
    }
}
