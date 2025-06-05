package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import com.plazacomidas.plazoleta.adapters.in.web.dto.TraceabilityResponseDto;
import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.entity.Traceability;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.repository.TraceabilityJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.Mockito.*;

class GetOrderTraceabilityUseCaseTest {

    @InjectMocks
    private GetOrderTraceabilityUseCase useCase;

    @Mock private OrderRepository orderRepository;
    @Mock private TraceabilityJpaRepository traceabilityRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTraceabilityForOrder_success() {
        Long orderId = 1L;
        Long clientId = 100L;

        Order order = Order.builder()
                .id(orderId)
                .clientId(clientId)
                .build();

        Traceability log1 = Traceability.builder()
                .date(LocalDateTime.now())
                .previousStatus("PENDIENTE")
                .newStatus("EN_PREPARACION")
                .employeeEmail("empleado1@mail.com")
                .build();

        Traceability log2 = Traceability.builder()
                .date(LocalDateTime.now())
                .previousStatus("EN_PREPARACION")
                .newStatus("LISTO")
                .employeeEmail("empleado2@mail.com")
                .build();

        when(orderRepository.getById(orderId)).thenReturn(order);
        when(traceabilityRepository.findByOrderId(orderId)).thenReturn(List.of(log1, log2));

        List<TraceabilityResponseDto> result = useCase.getTraceabilityForOrder(orderId, clientId);

        assertEquals(2, result.size());
        assertEquals("PENDIENTE", result.get(0).getPreviousStatus());
        assertEquals("LISTO", result.get(1).getNewStatus());
    }

    @Test
    void testGetTraceabilityForOrder_notOwnedByClient_throwsException() {
        Long orderId = 1L;
        Long clientId = 999L;

        Order order = Order.builder()
                .id(orderId)
                .clientId(100L)
                .build();

        when(orderRepository.getById(orderId)).thenReturn(order);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> useCase.getTraceabilityForOrder(orderId, clientId));

        assertEquals("El pedido no pertenece al cliente autenticado", ex.getMessage());
    }
}
