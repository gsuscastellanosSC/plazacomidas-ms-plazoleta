package com.plazacomidas.plazoleta.adapters.in.web.controller;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.mapper.RestaurantRequestMapper;
import com.plazacomidas.plazoleta.application.port.in.CreateRestaurantUseCasePort;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestaurantConstants.API_RESTAURANTS)
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRequestMapper mapper;
    private final CreateRestaurantUseCasePort createRestaurantUseCasePort;

    @PostMapping(RestaurantConstants.POST_CREATE_RESTAURANT)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantRequestDto dto) {

        Restaurant restaurant = mapper.toDomain(dto);

        return ResponseEntity.ok(createRestaurantUseCasePort.execute(restaurant));
    }
}
