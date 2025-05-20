package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;
import com.plazacomidas.plazoleta.domain.exception.UnauthorizedDishModificationException;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateDishUseCaseTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private UpdateDishUseCase updateDishUseCase;

    private Dish dish;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dish = Dish.builder()
                .id(1L)
                .nombre("Hamburguesa")
                .precio(20000)
                .descripcion("Con queso")
                .urlImagen("img.jpg")
                .idCategoria(1L)
                .restauranteId(100L)
                .activo(true)
                .build();

        restaurant = Restaurant.builder()
                .id(100L)
                .nombre("Restaurante A")
                .idPropietario(5L)
                .build();
    }

    @Test
    void testExecute_Success() {
        UpdateDishRequestDto dto = new UpdateDishRequestDto();
        dto.setPrice(2500);
        dto.setDescription("Con queso y tocineta");
        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(100L)).thenReturn(Optional.of(restaurant));

        updateDishUseCase.execute(1L, 5L, dto);

        assertEquals(2500, dish.getPrecio());
        assertEquals("Con queso y tocineta", dish.getDescripcion());
        verify(dishRepository).update(dish);
    }

    @Test
    void testExecute_DishNotFound() {
        UpdateDishRequestDto dto = new UpdateDishRequestDto();
        dto.setPrice(2500);
        dto.setDescription("Con queso");
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> updateDishUseCase.execute(1L, 5L, dto));

        assertEquals("El plato no existe.", ex.getMessage());
    }

    @Test
    void testExecute_RestaurantNotFound() {
        UpdateDishRequestDto dto = new UpdateDishRequestDto();
        dto.setPrice(25000);
        dto.setDescription("Con queso");

        dish = Dish.builder()
                .id(1L)
                .restauranteId(100L)
                .build();

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(100L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> updateDishUseCase.execute(1L, 5L, dto));

        assertEquals("El restaurante no existe.", ex.getMessage());
    }

    @Test
    void testExecute_UnauthorizedUser() {
        UpdateDishRequestDto dto = new UpdateDishRequestDto();
        dto.setPrice(25000);
        dto.setDescription("Con queso");

        dish = Dish.builder()
                .id(1L)
                .restauranteId(100L)
                .build();

        restaurant = new Restaurant();
        restaurant.setId(100L);
        restaurant.setIdPropietario(5L);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(restaurantRepository.findById(100L)).thenReturn(Optional.of(restaurant));

        Exception ex = assertThrows(UnauthorizedDishModificationException.class,
                () -> updateDishUseCase.execute(1L, 999L, dto)); // Usuario incorrecto

        assertInstanceOf(UnauthorizedDishModificationException.class, ex);
    }
}
