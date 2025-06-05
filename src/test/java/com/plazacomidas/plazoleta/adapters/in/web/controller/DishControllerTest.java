package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.DishRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;
import com.plazacomidas.plazoleta.application.port.in.ChangeDishAvailabilityUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetDishesUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.UpdateDishUseCasePort;
import com.plazacomidas.plazoleta.application.usecase.CreateDishUseCase;
import com.plazacomidas.plazoleta.domain.model.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import com.plazacomidas.plazoleta.adapters.in.web.dto.DishResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishControllerTest {

    @InjectMocks
    private DishController controller;

    @Mock
    private CreateDishUseCase createDishUseCase;
    @Mock
    private GetDishesUseCasePort getDishesUseCasePort;
    @Mock
    private UpdateDishUseCasePort updateDishUseCasePort;
    @Mock
    private ChangeDishAvailabilityUseCasePort changeDishAvailabilityUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearPlato() {
        DishRequestDto dto = new DishRequestDto("Hamburguesa", 15000, "Rica", "http://imagen.com", 1L, 2L);
        Dish expectedDish = Dish.builder().nombre("Hamburguesa").precio(15000).activo(true).build();

        when(createDishUseCase.execute(any(Dish.class), eq(1L))).thenReturn(expectedDish);

        Dish result = controller.crearPlato(1L, dto);

        assertEquals("Hamburguesa", result.getNombre());
        assertEquals(15000, result.getPrecio());
        assertTrue(result.isActivo());
    }

    @Test
    void testActualizarPlato() {
        UpdateDishRequestDto dto = new UpdateDishRequestDto(20000, "Actualizado");

        ResponseEntity<Void> response = controller.actualizarPlato(10L, dto, 2L);

        assertEquals(204, response.getStatusCodeValue());
        verify(updateDishUseCasePort).execute(10L, 2L, dto);
    }

    @Test
    void testChangeDishAvailability() {
        ResponseEntity<Void> response = controller.changeDishAvailability(5L, true, 99L);

        assertEquals(204, response.getStatusCodeValue());
        verify(changeDishAvailabilityUseCase).execute(5L, 99L, true);
    }

    @Test
    void testListDishesByRestaurant() {
        Dish dish = Dish.builder().nombre("Pizza").descripcion("Deliciosa").precio(20000).build();
        Page<Dish> page = new PageImpl<>(List.of(dish));
        when(getDishesUseCasePort.execute(3L, null, 0, 10)).thenReturn(page);

        ResponseEntity<Page<DishResponseDto>> response = controller.listDishesByRestaurant(3L, null, 0, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pizza", response.getBody().getContent().get(0).getNombre());
    }
}
