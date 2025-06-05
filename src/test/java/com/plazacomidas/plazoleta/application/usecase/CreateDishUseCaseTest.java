package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.validation.DishOwnerValidator;
import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CreateDishUseCaseTest {

    @InjectMocks
    private CreateDishUseCase useCase;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishOwnerValidator dishOwnerValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteShouldValidateOwnershipAndSaveDish() {
        Long userId = 1L;
        Long restaurantId = 10L;
        Dish dish = Dish.builder()
                .nombre("Pizza")
                .restauranteId(restaurantId)
                .build();

        when(dishRepository.save(dish)).thenReturn(dish);

        Dish result = useCase.execute(dish, userId);

        assertNotNull(result);
        assertEquals("Pizza", result.getNombre());

        verify(dishOwnerValidator).validateOwnership(userId, restaurantId);
        verify(dishRepository).save(dish);
    }
}
