package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.validation.RestaurantValidator;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantValidator restaurantValidator;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ValidateRestaurantOwnerService validateRestaurantOwnerService;

    @InjectMocks
    private CreateRestaurantUseCase useCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = Restaurant.builder()
                .idPropietario(123L)
                .build();
    }

    @Test
    void testExecute_ValidRestaurant_SavesRestaurant() {
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant result = useCase.execute(restaurant);

        verify(restaurantValidator).validateCreateRestaurant(restaurant);
        verify(validateRestaurantOwnerService).validate(123L);
        verify(restaurantRepository).save(restaurant);
        assertEquals(restaurant, result);
    }
}
