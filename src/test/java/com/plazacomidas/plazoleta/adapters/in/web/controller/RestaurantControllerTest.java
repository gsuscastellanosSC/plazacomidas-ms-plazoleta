package com.plazacomidas.plazoleta.adapters.in.web.controller;
import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.mapper.RestaurantRequestMapper;
import com.plazacomidas.plazoleta.application.port.in.CreateRestaurantUseCasePort;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

    @Mock
    private RestaurantRequestMapper mapper;

    @Mock
    private CreateRestaurantUseCasePort createRestaurantUseCasePort;

    @InjectMocks
    private RestaurantController controller;

    private RestaurantRequestDto requestDto;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new RestaurantRequestDto();
        restaurant = new Restaurant();
    }

    @Test
    void testCreate_ReturnsOkResponseWithRestaurant() {
        when(mapper.toDomain(requestDto)).thenReturn(restaurant);
        when(createRestaurantUseCasePort.execute(restaurant)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = controller.create(requestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(restaurant, response.getBody());

        verify(mapper).toDomain(requestDto);
        verify(createRestaurantUseCasePort).execute(restaurant);
    }
}
