package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.dto.OrderAndEmployeeDto;
import com.plazacomidas.plazoleta.application.dto.RegisterTraceabilityDto;
import com.plazacomidas.plazoleta.application.port.in.ChangeOrderStatusUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.OrderAndEmployeeFetcherPort;
import com.plazacomidas.plazoleta.application.port.in.RegisterOrderTraceUseCasePort;
import com.plazacomidas.plazoleta.application.port.out.SmsNotifierPort;
import com.plazacomidas.plazoleta.application.validation.CancelOrderFieldValidator;
import com.plazacomidas.plazoleta.application.validation.MarkOrderDeliveredValidator;
import com.plazacomidas.plazoleta.application.validation.MarkOrderReadyValidator;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeOrderStatusUseCase implements ChangeOrderStatusUseCasePort {

    private final OrderRepository orderRepository;
    private final SmsNotifierPort smsNotifierPort;
    private final OrderAndEmployeeFetcherPort orderAndEmployeeFetcher;
    private final MarkOrderReadyValidator markOrderReadyValidator;
    private final MarkOrderDeliveredValidator markOrderDeliveredValidator;
    private final RegisterOrderTraceUseCasePort registerTraceUseCase;

    @Override
    public void markOrderAsReady(Long orderId, Long employeeId) {
        OrderAndEmployeeDto orderAndEmployeeDto = orderAndEmployeeFetcher.fetch(orderId, employeeId);
        markOrderReadyValidator.validate(orderAndEmployeeDto);

        Order order = orderAndEmployeeDto.getOrder();
        order.setStatus(OrderStatus.LISTO);
        orderRepository.save(order);

        UserResponseDto client = orderAndEmployeeDto.getEmployee();

        registerTraceUseCase.registerTrace(RegisterTraceabilityDto.builder()
                .orderId(order.getId())
                .previousStatus(String.valueOf(OrderStatus.EN_PREPARACION))
                .newStatus(OrderStatus.LISTO.name())
                .clientId(order.getClientId())
                .clientEmail(client.getEmail())
                .employeeId(employeeId)
                .employeeEmail(orderAndEmployeeDto.getEmployee().getEmail())
                .build());

        smsNotifierPort.sendOrderReadySms(client.getPhoneNumber(), generatePin() );

    }

    @Override
    public void markOrderAsDelivered(Long orderId, Long employeeId, String pin) {
        OrderAndEmployeeDto dto = orderAndEmployeeFetcher.fetch(orderId, employeeId);
        markOrderDeliveredValidator.validate(dto, pin);
        Order order = dto.getOrder();
        order.setStatus(OrderStatus.ENTREGADO);
        orderRepository.save(order);

        UserResponseDto client = dto.getEmployee();

        registerTraceUseCase.registerTrace(RegisterTraceabilityDto.builder()
                .orderId(order.getId())
                .previousStatus(String.valueOf(OrderStatus.LISTO))
                .newStatus(OrderStatus.ENTREGADO.name())
                .clientId(order.getClientId())
                .clientEmail(client.getEmail())
                .employeeId(employeeId)
                .employeeEmail(dto.getEmployee().getEmail())
                .build());
    }

    @Override
    public void cancelOrder(Long orderId, Long clientId) {
        Order order = orderRepository.getById(orderId);

        CancelOrderFieldValidator.ORDER_IS_NOT_NULL.validate(order);
        CancelOrderFieldValidator.ORDER_STATUS_IS_PENDING.validate(order);
        CancelOrderFieldValidator.ORDER_BELONGS_TO_CLIENT.validate(order.getClientId().equals(clientId));

        order.setStatus(OrderStatus.CANCELADO);
        orderRepository.save(order);

        registerTraceUseCase.registerTrace(RegisterTraceabilityDto.builder()
                .orderId(order.getId())
                .previousStatus(String.valueOf(OrderStatus.PENDIENTE))
                .newStatus(OrderStatus.CANCELADO.name())
                .clientId(order.getClientId())
                .clientEmail(null)
                .employeeId(null)
                .employeeEmail(null)
                .build());
    }

    private String generatePin() {
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }
}
