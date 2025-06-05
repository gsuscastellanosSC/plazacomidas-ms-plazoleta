package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.mapper.RestaurantRequestMapper;
import com.plazacomidas.plazoleta.application.port.in.CreateRestaurantUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetOrderEfficiencyUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetRestaurantsUseCasePort;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.common.SecurityExpressions;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.model.RestaurantModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestaurantConstants.API_RESTAURANTS)
@RequiredArgsConstructor
@Tag(name = "Restaurantes", description = "Operaciones relacionadas con restaurantes")
public class RestaurantController {

    private final RestaurantRequestMapper mapper;
    private final GetRestaurantsUseCasePort getRestaurantsUseCasePort;
    private final CreateRestaurantUseCasePort createRestaurantUseCasePort;
    private final GetOrderEfficiencyUseCasePort getOrderEfficiencyUseCase;

    @Operation(summary = "Crear restaurante", description = "Permite a un propietario registrar un nuevo restaurante.")
    @ApiResponse(responseCode = "200", description = "Restaurante creado exitosamente")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_PROPIETARIO)
    @PostMapping(RestaurantConstants.POST_CREATE_RESTAURANT)
    public ResponseEntity<Restaurant> create(
            @Parameter(description = "Datos del restaurante a registrar", required = true)
            @Valid @RequestBody RestaurantRequestDto dto) {
        Restaurant restaurant = mapper.toDomain(dto);
        return ResponseEntity.ok(createRestaurantUseCasePort.execute(restaurant));
    }

    @Operation(summary = "Listar restaurantes", description = "Devuelve una lista paginada de restaurantes visibles para clientes.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    public ResponseEntity<Page<RestaurantResponseDto>> getRestaurants(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size) {

        Page<RestaurantModel> restaurantPage = getRestaurantsUseCasePort.execute(page, size);
        Page<RestaurantResponseDto> response = restaurantPage.map(restaurant ->
                new RestaurantResponseDto(restaurant.getNombre(), restaurant.getUrlLogo()));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Consultar eficiencia de pedidos", description = "Devuelve el promedio de tiempo de atención por empleado y total del restaurante.")
    @ApiResponse(responseCode = "200", description = "Eficiencia obtenida correctamente")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_PROPIETARIO)
    @GetMapping("/{restaurantId}/efficiency")
    public ResponseEntity<OrderEfficiencyResponseDto> getEfficiency(
            @Parameter(description = "ID del restaurante", required = true)
            @PathVariable Long restaurantId,

            @Parameter(description = "ID del propietario", required = true)
            @RequestHeader("owner-id") Long ownerId) {

        OrderEfficiencyResponseDto response = getOrderEfficiencyUseCase.getEfficiency(restaurantId, ownerId);
        return ResponseEntity.ok(response);
    }
}
