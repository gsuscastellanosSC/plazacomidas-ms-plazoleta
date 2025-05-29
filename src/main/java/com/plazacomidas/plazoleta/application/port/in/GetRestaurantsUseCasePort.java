package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;

public interface GetRestaurantsUseCasePort {
    Page<RestaurantModel> execute(int page, int size);
}
