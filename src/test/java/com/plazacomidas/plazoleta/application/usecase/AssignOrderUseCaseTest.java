package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import com.plazacomidas.plazoleta.application.validation.AssignOrderValidator;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

class AssignOrderUseCaseTest {

    @InjectMocks
    private AssignOrderUseCase useCase;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AssignOrderValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignOrder_success() {
        Long orderId = 1L;
        Long employeeId = 77L;

        Order order = Order.builder()
                .id(orderId)
                .clientId(200L)
                .restaurantId(300L)
                .chefId(555L)
                .status(OrderStatus.PENDIENTE)
                .creationDate(LocalDateTime.now())
                .dishes(Collections.emptyList())
                .build();

        when(validator.validate(orderId, employeeId)).thenReturn(order);

        useCase.assignOrder(orderId, employeeId);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());

        Order savedOrder = orderCaptor.getValue();
        assertEquals(orderId, savedOrder.getId());
        assertEquals(OrderStatus.EN_PREPARACION, savedOrder.getStatus());
        assertEquals(employeeId, savedOrder.getAssignedEmployeeId());
    }

    @Test
    void testAssignOrder_validatorThrowsException() {
        Long orderId = 1L;
        Long employeeId = 77L;

        when(validator.validate(orderId, employeeId))
                .thenThrow(new IllegalArgumentException("Pedido inválido"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                useCase.assignOrder(orderId, employeeId));

        assertEquals("Pedido inválido", ex.getMessage());
        verify(orderRepository, never()).save(any());
    }
}
