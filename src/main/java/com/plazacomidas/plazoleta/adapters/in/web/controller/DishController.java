package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.DishRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.DishResponseDto;
import com.plazacomidas.plazoleta.application.port.in.ChangeDishAvailabilityUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetDishesUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.UpdateDishUseCasePort;
import com.plazacomidas.plazoleta.application.usecase.CreateDishUseCase;
import com.plazacomidas.plazoleta.application.validation.DishValidator;
import com.plazacomidas.plazoleta.common.SecurityExpressions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import com.plazacomidas.plazoleta.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
@RequiredArgsConstructor
@Tag(name = "Platos", description = "Operaciones relacionadas con los platos")
public class DishController {

    private final CreateDishUseCase createDishUseCase;
    private final GetDishesUseCasePort getDishesUseCasePort;
    private final UpdateDishUseCasePort updateDishUseCasePort;
    private final ChangeDishAvailabilityUseCasePort changeDishAvailabilityUseCase;

    @Operation(
            summary = "Crear un nuevo plato",
            description = "Crea un plato para un restaurante si el usuario es propietario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plato creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    @PostMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_PROPIETARIO)
    public Dish crearPlato(
            @Parameter(description = "ID del propietario", required = true)
            @RequestHeader("user-id") Long userId,

            @Parameter(description = "Datos del plato a crear", required = true)
            @RequestBody DishRequestDto dto) {

        DishValidator.validateCreateDish(dto);

        Dish dish = Dish.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .descripcion(dto.getDescripcion())
                .urlImagen(dto.getUrlImagen())
                .idCategoria(dto.getIdCategoria())
                .restauranteId(dto.getRestauranteId())
                .activo(true)
                .build();

        return createDishUseCase.execute(dish, userId);
    }

    @Operation(
            summary = "Actualizar un plato existente",
            description = "Actualiza precio y descripción de un plato si el usuario es propietario",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Plato actualizado"),
                    @ApiResponse(responseCode = "403", description = "No autorizado"),
                    @ApiResponse(responseCode = "404", description = "Plato no encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPlato(
            @Parameter(description = "ID del plato a actualizar", required = true)
            @PathVariable Long id,

            @Parameter(description = "Datos a actualizar", required = true)
            @RequestBody UpdateDishRequestDto dto,

            @Parameter(description = "ID del propietario", required = true)
            @RequestHeader("user-id") Long userId
    ) {
        updateDishUseCasePort.execute(id, userId, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cambiar disponibilidad de un plato",
            description = "Habilita o deshabilita un plato para la venta",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Disponibilidad actualizada"),
                    @ApiResponse(responseCode = "403", description = "No autorizado"),
                    @ApiResponse(responseCode = "404", description = "Plato no encontrado")
            }
    )
    @PatchMapping("/{dishId}/availability")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_PROPIETARIO)
    public ResponseEntity<Void> changeDishAvailability(
            @Parameter(description = "ID del plato", required = true)
            @PathVariable Long dishId,

            @Parameter(description = "Estado de disponibilidad", required = true)
            @RequestParam boolean active,

            @Parameter(description = "ID del propietario", required = true)
            @RequestHeader("user-id") Long userId
    ) {
        changeDishAvailabilityUseCase.execute(dishId, userId, active);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar platos de un restaurante",
            description = "Devuelve una lista paginada de platos visibles para los clientes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de platos obtenido")
            }
    )
    @GetMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    public ResponseEntity<Page<DishResponseDto>> listDishesByRestaurant(
            @Parameter(description = "ID del restaurante", required = true)
            @RequestParam Long restauranteId,

            @Parameter(description = "Categoría del plato")
            @RequestParam(required = false) String categoria,

            @Parameter(description = "Número de página")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Tamaño de página")
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Dish> dishes = getDishesUseCasePort.execute(restauranteId, categoria, page, size);
        Page<DishResponseDto> response = dishes.map(
                d -> new DishResponseDto(d.getNombre(), d.getDescripcion(), d.getPrecio()));
        return ResponseEntity.ok(response);
    }
}
