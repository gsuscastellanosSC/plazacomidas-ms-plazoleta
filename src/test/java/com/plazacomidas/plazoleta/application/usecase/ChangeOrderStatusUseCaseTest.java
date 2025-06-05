package com.plazacomidas.plazoleta.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.dto.OrderAndEmployeeDto;
import com.plazacomidas.plazoleta.application.dto.RegisterTraceabilityDto;
import com.plazacomidas.plazoleta.application.port.in.OrderAndEmployeeFetcherPort;
import com.plazacomidas.plazoleta.application.port.in.RegisterOrderTraceUseCasePort;
import com.plazacomidas.plazoleta.application.port.out.SmsNotifierPort;
import com.plazacomidas.plazoleta.application.validation.MarkOrderDeliveredValidator;
import com.plazacomidas.plazoleta.application.validation.MarkOrderReadyValidator;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class ChangeOrderStatusUseCaseTest {

    @InjectMocks
    private ChangeOrderStatusUseCase useCase;

    @Mock private OrderRepository orderRepository;
    @Mock private SmsNotifierPort smsNotifierPort;
    @Mock private OrderAndEmployeeFetcherPort fetcherPort;
    @Mock private MarkOrderReadyValidator readyValidator;
    @Mock private MarkOrderDeliveredValidator deliveredValidator;
    @Mock private RegisterOrderTraceUseCasePort traceUseCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarkOrderAsReady_success() {
        Long orderId = 1L;
        Long employeeId = 2L;

        Order order = Order.builder()
                .id(orderId)
                .status(OrderStatus.EN_PREPARACION)
                .clientId(10L)
                .build();

        UserResponseDto employee = new UserResponseDto();
        employee.setEmail("empleado@email.com");
        employee.setPhoneNumber("123456789");

        OrderAndEmployeeDto dto = OrderAndEmployeeDto
                .builder()
                .order(order)
                .employee(employee)
                .build();

        when(fetcherPort.fetch(orderId, employeeId)).thenReturn(dto);

        useCase.markOrderAsReady(orderId, employeeId);

        assertEquals(OrderStatus.LISTO, order.getStatus());
        verify(orderRepository).save(order);
        verify(traceUseCase).registerTrace(any(RegisterTraceabilityDto.class));
        verify(smsNotifierPort).sendOrderReadySms(eq("123456789"), anyString());
    }

    @Test
    void testMarkOrderAsDelivered_success() {
        Long orderId = 1L;
        Long employeeId = 2L;
        String pin = "1234";

        Order order = Order.builder()
                .id(orderId)
                .status(OrderStatus.LISTO)
                .clientId(10L)
                .build();

        UserResponseDto employee = new UserResponseDto();
        employee.setEmail("empleado@email.com");

        OrderAndEmployeeDto dto = OrderAndEmployeeDto
                .builder()
                .order(order)
                .employee(employee)
                .build();

        when(fetcherPort.fetch(orderId, employeeId)).thenReturn(dto);

        useCase.markOrderAsDelivered(orderId, employeeId, pin);

        assertEquals(OrderStatus.ENTREGADO, order.getStatus());
        verify(orderRepository).save(order);
        verify(traceUseCase).registerTrace(any(RegisterTraceabilityDto.class));
        verify(deliveredValidator).validate(dto, pin);
    }

    @Test
    void testCancelOrder_success() {
        Long orderId = 1L;
        Long clientId = 10L;

        Order order = Order.builder()
                .id(orderId)
                .clientId(clientId)
                .status(OrderStatus.PENDIENTE)
                .build();

        when(orderRepository.getById(orderId)).thenReturn(order);

        useCase.cancelOrder(orderId, clientId);

        assertEquals(OrderStatus.CANCELADO, order.getStatus());
        verify(orderRepository).save(order);
        verify(traceUseCase).registerTrace(any(RegisterTraceabilityDto.class));
    }
}
