package com.plazacomidas.plazoleta.adapters.in.web.controller;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.mapper.RestaurantRequestMapper;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantResponseDto;
import com.plazacomidas.plazoleta.application.port.in.CreateRestaurantUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetOrderEfficiencyUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetRestaurantsUseCasePort;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.common.SecurityExpressions;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.model.RestaurantModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestaurantConstants.API_RESTAURANTS)
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRequestMapper mapper;
    private final GetRestaurantsUseCasePort getRestaurantsUseCasePort;
    private final CreateRestaurantUseCasePort createRestaurantUseCasePort;
    private final GetOrderEfficiencyUseCasePort getOrderEfficiencyUseCase;

    @PreAuthorize(SecurityExpressions.HAS_ROLE_PROPIETARIO)
    @PostMapping(RestaurantConstants.POST_CREATE_RESTAURANT)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantRequestDto dto) {

        Restaurant restaurant = mapper.toDomain(dto);

        return ResponseEntity.ok(createRestaurantUseCasePort.execute(restaurant));
    }

    @GetMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    public ResponseEntity<Page<RestaurantResponseDto>> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<RestaurantModel> restaurantPage = getRestaurantsUseCasePort.execute(page, size);
        Page<RestaurantResponseDto> response = restaurantPage.map(restaurant ->
                new RestaurantResponseDto(restaurant.getNombre(), restaurant.getUrlLogo()));
        return ResponseEntity.ok(response);
    }


    @PreAuthorize(SecurityExpressions.HAS_ROLE_PROPIETARIO)
    @GetMapping("/{restaurantId}/efficiency")
    public ResponseEntity<OrderEfficiencyResponseDto> getEfficiency(
            @PathVariable Long restaurantId,
            @RequestHeader("owner-id") Long ownerId) {

        OrderEfficiencyResponseDto response = getOrderEfficiencyUseCase.getEfficiency(restaurantId, ownerId);
        return ResponseEntity.ok(response);
    }
}
