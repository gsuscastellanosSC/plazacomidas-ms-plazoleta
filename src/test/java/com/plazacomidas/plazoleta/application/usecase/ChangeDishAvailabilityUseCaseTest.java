package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.domain.exception.BusinessException;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChangeDishAvailabilityUseCaseTest {

    @InjectMocks
    private ChangeDishAvailabilityUseCase useCase;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ShouldChangeAvailability() {
        Long dishId = 1L;
        Long userId = 100L;
        boolean active = false;

        Dish dish = Dish.builder().id(dishId).activo(true).restauranteId(10L).build();
        Restaurant restaurant = Restaurant.builder().id(10L).idPropietario(userId).build();

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        useCase.execute(dishId, userId, active);

        assertFalse(dish.isActivo());
        verify(dishRepository).update(dish);
    }

    @Test
    void testExecute_ShouldThrowWhenDishNotFound() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                useCase.execute(1L, 100L, true));

        assertEquals("El plato no existe.", ex.getMessage());
    }

    @Test
    void testExecute_ShouldThrowWhenRestaurantNotFound() {
        Dish dish = Dish.builder().id(1L).restauranteId(999L).build();
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () ->
                useCase.execute(1L, 100L, true));
    }

    @Test
    void testExecute_ShouldThrowWhenUserIsNotOwner() {
        Dish dish = Dish.builder().id(1L).restauranteId(10L).build();
        Restaurant restaurant = Restaurant.builder().id(10L).idPropietario(999L).build();

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));

        assertThrows(InvalidCredentialsException.class, () ->
                useCase.execute(1L, 100L, true));
    }
}
