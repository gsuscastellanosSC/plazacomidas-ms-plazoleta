package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.domain.model.Dish;
import com.plazacomidas.plazoleta.domain.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetDishesUseCaseTest {

    @InjectMocks
    private GetDishesUseCase useCase;

    @Mock
    private DishRepository dishRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_ShouldReturnPageOfDishes() {
        Long restauranteId = 1L;
        String categoria = "Comida rápida";
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);

        Dish dish1 = Dish.builder().nombre("Hamburguesa").build();
        Dish dish2 = Dish.builder().nombre("Perro caliente").build();
        Page<Dish> dishPage = new PageImpl<>(List.of(dish1, dish2), pageable, 2);

        when(dishRepository.findByRestaurantIdAndCategoria(restauranteId, categoria, pageable)).thenReturn(dishPage);

        Page<Dish> result = useCase.execute(restauranteId, categoria, page, size);

        assertEquals(2, result.getTotalElements());
        assertEquals("Hamburguesa", result.getContent().get(0).getNombre());
        assertEquals("Perro caliente", result.getContent().get(1).getNombre());

        verify(dishRepository).findByRestaurantIdAndCategoria(restauranteId, categoria, pageable);
    }
}
