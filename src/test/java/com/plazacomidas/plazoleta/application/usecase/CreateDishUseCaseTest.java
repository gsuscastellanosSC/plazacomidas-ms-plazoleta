package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.validation.DishOwnerValidator;
import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import com.plazacomidas.plazoleta.domain.exception.UnauthorizedDishCreationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateDishUseCaseTest {
    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishOwnerValidator dishOwnerValidator;

    @InjectMocks
    private CreateDishUseCase createDishUseCase;

    private Dish dish;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dish = Dish.builder()
                .id(1L)
                .nombre("Hamburguesa")
                .precio(20000)
                .descripcion("Con queso y papas")
                .urlImagen("http://imagen.com/hamburguesa.jpg")
                .idCategoria(1L)
                .restauranteId(10L)
                .activo(true)
                .build();
    }

    @Test
    void testCreateDishSuccess() {
        Long userId = 5L;

        doNothing().when(dishOwnerValidator).validateOwnership(userId, dish.getRestauranteId());

        when(dishRepository.save(dish)).thenReturn(dish);

        Dish result = createDishUseCase.execute(dish, userId);

        assertEquals(dish, result);
        verify(dishOwnerValidator).validateOwnership(userId, dish.getRestauranteId());
        verify(dishRepository).save(dish);
    }

    @Test
    void testCreateDishUnauthorized() {
        Long userId = 99L;

        doThrow(new UnauthorizedDishCreationException("No es el propietario del restaurante"))
                .when(dishOwnerValidator).validateOwnership(userId, dish.getRestauranteId());

        UnauthorizedDishCreationException exception = assertThrows(
                UnauthorizedDishCreationException.class,
                () -> createDishUseCase.execute(dish, userId)
        );

        assertEquals("No es el propietario del restaurante", exception.getMessage());
        verify(dishOwnerValidator).validateOwnership(userId, dish.getRestauranteId());
        verify(dishRepository, never()).save(any());
    }
}
