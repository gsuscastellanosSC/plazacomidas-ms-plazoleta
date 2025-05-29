package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.port.in.GetRestaurantsUseCasePort;
import com.plazacomidas.plazoleta.domain.model.RestaurantModel;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class GetRestaurantsUseCase implements GetRestaurantsUseCasePort {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<RestaurantModel> execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        return restaurantRepository.findAll(pageable);
    }
}
