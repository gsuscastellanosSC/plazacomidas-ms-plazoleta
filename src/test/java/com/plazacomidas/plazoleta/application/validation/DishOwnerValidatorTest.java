package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.domain.exception.UnauthorizedDishCreationException;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DishOwnerValidatorTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    private DishOwnerValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new DishOwnerValidator(restaurantRepository);
    }

    @Test
    void validateOwnership_success() {
        Long userId = 5L;
        Long restaurantId = 10L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setIdPropietario(userId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        assertDoesNotThrow(() -> validator.validateOwnership(userId, restaurantId));
        verify(restaurantRepository).findById(restaurantId);
    }

    @Test
    void validateOwnership_restaurantNotFound() {
        Long userId = 5L;
        Long restaurantId = 10L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        UnauthorizedDishCreationException ex = assertThrows(
                UnauthorizedDishCreationException.class,
                () -> validator.validateOwnership(userId, restaurantId)
        );

        assertEquals("Restaurante no encontrado", ex.getMessage());
        verify(restaurantRepository).findById(restaurantId);
    }

    @Test
    void validateOwnership_notOwner() {
        Long userId = 99L;
        Long restaurantId = 10L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setIdPropietario(5L);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        UnauthorizedDishCreationException ex = assertThrows(
                UnauthorizedDishCreationException.class,
                () -> validator.validateOwnership(userId, restaurantId)
        );

        assertEquals("No es el propietario del restaurante", ex.getMessage());
        verify(restaurantRepository).findById(restaurantId);
    }
}
