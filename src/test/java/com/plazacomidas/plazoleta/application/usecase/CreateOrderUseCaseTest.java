package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.application.validation.OrderValidator;
import com.plazacomidas.plazoleta.domain.factory.OrderFactory;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateOrderUseCaseTest {

    @InjectMocks
    private CreateOrderUseCase useCase;

    @Mock
    private OrderValidator orderValidator;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderFactory orderFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder_success() {
        Long clientId = 101L;
        CreateOrderRequestDto request =CreateOrderRequestDto.builder().build();

        Order mockOrder = Order.builder().clientId(clientId).build();

        doNothing().when(orderValidator).validateCreateOrder(clientId, request);
        when(orderFactory.create(clientId, request)).thenReturn(mockOrder);

        useCase.createOrder(clientId, request);

        verify(orderValidator).validateCreateOrder(clientId, request);
        verify(orderFactory).create(clientId, request);
        verify(orderRepository).save(mockOrder);
    }

    @Test
    void testCreateOrder_validationFails() {
        Long clientId = 102L;
        CreateOrderRequestDto request =CreateOrderRequestDto.builder().build();

        doThrow(new IllegalArgumentException("Datos inválidos")).when(orderValidator).validateCreateOrder(clientId, request);

        assertThrows(IllegalArgumentException.class, () ->
                useCase.createOrder(clientId, request));

        verify(orderValidator).validateCreateOrder(clientId, request);
        verify(orderFactory, never()).create(any(), any());
        verify(orderRepository, never()).save(any());
    }
}
