package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import com.plazacomidas.plazoleta.application.validation.RestaurantValidator;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

class CreateRestaurantUseCaseTest {

    @InjectMocks
    private CreateRestaurantUseCase useCase;

    @Mock
    private RestaurantValidator restaurantValidator;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ValidateRestaurantOwnerService validateRestaurantOwnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_validRestaurant_savesAndReturnsRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setIdPropietario(1L);
        restaurant.setNombre("Mi Restaurante");

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant result = useCase.execute(restaurant);

        verify(restaurantValidator).validateCreateRestaurant(restaurant);
        verify(validateRestaurantOwnerService).validate(1L);
        verify(restaurantRepository).save(restaurant);

        assertNotNull(result);
        assertEquals("Mi Restaurante", result.getNombre());
    }
}
