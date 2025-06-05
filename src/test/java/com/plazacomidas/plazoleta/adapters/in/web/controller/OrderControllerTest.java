package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.DeliverOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.TraceabilityResponseDto;
import com.plazacomidas.plazoleta.application.port.in.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    @InjectMocks
    private OrderController controller;

    @Mock
    private AssignOrderUseCasePort assignOrderUseCasePort;

    @Mock
    private CreateOrderUseCasePort createOrderUseCase;

    @Mock
    private GetOrdersUseCasePort getOrdersUseCase;

    @Mock
    private ChangeOrderStatusUseCasePort changeOrderStatusUseCase;

    @Mock
    private GetOrderTraceabilityUseCasePort getTraceabilityUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        CreateOrderRequestDto request = CreateOrderRequestDto.builder().build();
        controller.createOrder(1L, request);
        verify(createOrderUseCase).createOrder(1L, request);
    }

    @Test
    void testGetOrdersByStatus() {
        OrderResponseDto dto = new OrderResponseDto();
        Page<OrderResponseDto> page = new PageImpl<>(List.of(dto));

        when(getOrdersUseCase.getOrdersByStatusForEmployee(5L, "PENDIENTE", 0, 10)).thenReturn(page);

        Page<OrderResponseDto> result = controller.getOrdersByStatus(5L, "PENDIENTE", 0, 10);

        assertEquals(1, result.getContent().size());
        verify(getOrdersUseCase).getOrdersByStatusForEmployee(5L, "PENDIENTE", 0, 10);
    }

    @Test
    void testAssignOrderToEmployee() {
        ResponseEntity<Void> response = controller.assignOrderToEmployee(100L, 8L);
        assertEquals(200, response.getStatusCodeValue());
        verify(assignOrderUseCasePort).assignOrder(100L, 8L);
    }

    @Test
    void testMarkOrderAsReady() {
        ResponseEntity<Void> response = controller.markOrderAsReady(77L, 4L);
        assertEquals(200, response.getStatusCodeValue());
        verify(changeOrderStatusUseCase).markOrderAsReady(77L, 4L);
    }

    @Test
    void testDeliverOrder() {
        DeliverOrderRequestDto request = new DeliverOrderRequestDto();
        request.setPin("1234");

        ResponseEntity<Void> response = controller.deliverOrder(55L, 9L, request);

        assertEquals(200, response.getStatusCodeValue());
        verify(changeOrderStatusUseCase).markOrderAsDelivered(55L, 9L, "1234");
    }

    @Test
    void testCancelOrder() {
        ResponseEntity<Void> response = controller.cancelOrder(42L, 3L);
        assertEquals(200, response.getStatusCodeValue());
        verify(changeOrderStatusUseCase).cancelOrder(42L, 3L);
    }

    @Test
    void testGetOrderTraceability() {
        TraceabilityResponseDto log = TraceabilityResponseDto.builder().build();
        when(getTraceabilityUseCase.getTraceabilityForOrder(200L, 7L)).thenReturn(List.of(log));

        ResponseEntity<List<TraceabilityResponseDto>> response = controller.getOrderTraceability(7L, 200L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(getTraceabilityUseCase).getTraceabilityForOrder(200L, 7L);
    }
}
