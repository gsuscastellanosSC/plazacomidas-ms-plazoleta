package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.DishRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.DishResponseDto;
import com.plazacomidas.plazoleta.application.port.in.ChangeDishAvailabilityUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetDishesUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.UpdateDishUseCasePort;
import com.plazacomidas.plazoleta.application.usecase.CreateDishUseCase;
import com.plazacomidas.plazoleta.application.validation.DishValidator;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import com.plazacomidas.plazoleta.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
@RequiredArgsConstructor
public class DishController {

    private final CreateDishUseCase createDishUseCase;
    private final  GetDishesUseCasePort getDishesUseCasePort;
    private final UpdateDishUseCasePort updateDishUseCasePort;
    private final ChangeDishAvailabilityUseCasePort changeDishAvailabilityUseCase;


    @PostMapping
    @PreAuthorize("hasRole('PROPIETARIO')")
    public Dish crearPlato(@RequestHeader("user-id") Long userId,
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPlato(
            @PathVariable Long id,
            @RequestBody UpdateDishRequestDto dto,
            @RequestHeader("user-id") Long userId
    ) {
        updateDishUseCasePort.execute(id, userId, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('PROPIETARIO')")
    @PatchMapping("/{dishId}/availability")
    public ResponseEntity<Void> changeDishAvailability(
            @PathVariable Long dishId,
            @RequestParam boolean active,
            @RequestHeader("user-id") Long userId
    ) {
        changeDishAvailabilityUseCase.execute(dishId, userId, active);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping
    public ResponseEntity<Page<DishResponseDto>> listDishesByRestaurant(
            @RequestParam Long restauranteId,
            @RequestParam(required = false) String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Dish> dishes = getDishesUseCasePort.execute(restauranteId, categoria, page, size);
        Page<DishResponseDto> response = dishes.map(
                d -> new DishResponseDto(d.getNombre(), d.getDescripcion(), d.getPrecio()));
        return ResponseEntity.ok(response);
    }
}
