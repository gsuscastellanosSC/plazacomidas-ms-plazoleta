package com.plazacomidas.plazoleta.adapters.in.web.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.mapper.RestaurantRequestMapper;
import com.plazacomidas.plazoleta.application.port.in.CreateRestaurantUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetOrderEfficiencyUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetRestaurantsUseCasePort;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import com.plazacomidas.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController controller;

    @Mock
    private RestaurantRequestMapper mapper;

    @Mock
    private GetRestaurantsUseCasePort getRestaurantsUseCasePort;

    @Mock
    private CreateRestaurantUseCasePort createRestaurantUseCasePort;

    @Mock
    private GetOrderEfficiencyUseCasePort getOrderEfficiencyUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRestaurant() {
        RestaurantRequestDto dto = new RestaurantRequestDto();
        Restaurant restaurant = Restaurant.builder().nombre("Rico's").build();

        when(mapper.toDomain(dto)).thenReturn(restaurant);
        when(createRestaurantUseCasePort.execute(restaurant)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = controller.create(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Rico's", response.getBody().getNombre());

        verify(mapper).toDomain(dto);
        verify(createRestaurantUseCasePort).execute(restaurant);
    }

    @Test
    void testGetRestaurants() {
        RestaurantModel model = RestaurantModel.builder().nombre("Rico's").urlLogo("http://img.com").build();
        Page<RestaurantModel> page = new PageImpl<>(List.of(model));

        when(getRestaurantsUseCasePort.execute(0, 10)).thenReturn(page);

        ResponseEntity<Page<RestaurantResponseDto>> response = controller.getRestaurants(0, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("Rico's", response.getBody().getContent().get(0).getNombre());
    }

    @Test
    void testGetEfficiency() {
        OrderEfficiencyResponseDto efficiency = OrderEfficiencyResponseDto.builder().build();
        when(getOrderEfficiencyUseCase.getEfficiency(1L, 99L)).thenReturn(efficiency);

        ResponseEntity<OrderEfficiencyResponseDto> response = controller.getEfficiency(1L, 99L);

        assertEquals(200, response.getStatusCodeValue());
        assertSame(efficiency, response.getBody());
        verify(getOrderEfficiencyUseCase).getEfficiency(1L, 99L);
    }
}
