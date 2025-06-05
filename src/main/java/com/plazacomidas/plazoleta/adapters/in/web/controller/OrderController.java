package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.DeliverOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.TraceabilityResponseDto;
import com.plazacomidas.plazoleta.application.port.in.*;
import com.plazacomidas.plazoleta.common.OrderConstants;
import com.plazacomidas.plazoleta.common.SecurityExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(OrderConstants.API_ORDERS)
@RequiredArgsConstructor
public class OrderController {

    private final AssignOrderUseCasePort assignOrderUseCasePort;
    private final CreateOrderUseCasePort createOrderUseCase;
    private final GetOrdersUseCasePort getOrdersUseCase;
    private final ChangeOrderStatusUseCasePort ChangeOrderStatusUseCase;
    private final GetOrderTraceabilityUseCasePort getTraceabilityUseCase;


    @PostMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader("client-id") Long clientId,
                            @RequestBody CreateOrderRequestDto request) {
        createOrderUseCase.createOrder(clientId, request);
    }

    @GetMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    public Page<OrderResponseDto> getOrdersByStatus(
            @RequestHeader("employee-id") Long employeeId,
            @RequestParam("status") String status,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        return getOrdersUseCase.getOrdersByStatusForEmployee(employeeId, status, page, size);
    }

    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    @GetMapping("/{orderId}/assign")
    public ResponseEntity<Void> assignOrderToEmployee(
            @PathVariable Long orderId,
            @RequestHeader("employee-id") Long employeeId) {
        assignOrderUseCasePort.assignOrder(orderId, employeeId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    @PostMapping("/{orderId}/ready")
    public ResponseEntity<Void> markOrderAsReady(
            @PathVariable Long orderId,
            @RequestHeader("employee-id") Long employeeId) {
        ChangeOrderStatusUseCase.markOrderAsReady(orderId, employeeId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<Void> deliverOrder(
            @PathVariable Long orderId,
            @RequestHeader("employee-id") Long employeeId,
            @RequestBody DeliverOrderRequestDto requestDto) {
        ChangeOrderStatusUseCase.markOrderAsDelivered(orderId, employeeId, requestDto.getPin());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId,
            @RequestHeader("client-id") Long clientId) {
        ChangeOrderStatusUseCase.cancelOrder(orderId, clientId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{orderId}/trazabilidad")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    public ResponseEntity<List<TraceabilityResponseDto>> getOrderTraceability(
            @RequestHeader("client-id") Long clientId,
            @PathVariable Long orderId) {

        List<TraceabilityResponseDto> logs = getTraceabilityUseCase.getTraceabilityForOrder(orderId, clientId);
        return ResponseEntity.ok(logs);
    }
}
