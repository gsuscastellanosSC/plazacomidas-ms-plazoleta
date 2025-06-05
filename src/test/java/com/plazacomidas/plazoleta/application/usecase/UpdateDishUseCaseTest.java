package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateDishUseCaseTest {

    @InjectMocks
    private UpdateDishUseCase useCase;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ShouldUpdateDishSuccessfully() {
        Long dishId = 1L;
        Long userId = 100L;

        Dish existingDish = Dish.builder()
                .id(dishId)
                .precio(10000)
                .descripcion("Viejo")
                .restauranteId(10L)
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(10L)
                .idPropietario(userId)
                .build();

        UpdateDishRequestDto dto = new UpdateDishRequestDto(20000, "Nuevo");

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(existingDish));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        useCase.execute(dishId, userId, dto);

        assertEquals(20000, existingDish.getPrecio());
        assertEquals("Nuevo", existingDish.getDescripcion());

        verify(dishRepository).update(existingDish);
    }

    @Test
    void testExecute_ShouldThrowIfDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                useCase.execute(1L, 10L, new UpdateDishRequestDto(1234, "Desc")));

        assertEquals("El plato no existe.", ex.getMessage());
    }

    @Test
    void testExecute_ShouldThrowIfRestaurantNotFound() {
        Dish dish = Dish.builder().id(1L).restauranteId(999L).build();
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(999L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                useCase.execute(1L, 10L, new UpdateDishRequestDto(1234, "Desc")));

        assertEquals("El restaurante no existe.", ex.getMessage());
    }

    @Test
    void testExecute_ShouldThrowIfUserNotOwner() {
        Dish dish = Dish.builder().id(1L).restauranteId(10L).build();
        Restaurant restaurant = Restaurant.builder().id(10L).idPropietario(999L).build();

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        assertThrows(InvalidCredentialsException.class, () ->
                useCase.execute(1L, 10L, new UpdateDishRequestDto(1234, "Desc")));
    }
}
