package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import com.plazacomidas.plazoleta.application.port.in.AssignOrderUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.ChangeOrderStatusUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.CreateOrderUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.GetOrdersUseCasePort;
import com.plazacomidas.plazoleta.common.OrderConstants;
import com.plazacomidas.plazoleta.common.SecurityExpressions;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(OrderConstants.API_ORDERS)
@RequiredArgsConstructor
public class OrderController {

    private final AssignOrderUseCasePort assignOrderUseCasePort;
    private final CreateOrderUseCasePort createOrderUseCase;
    private final GetOrdersUseCasePort getOrdersUseCase;
    private final ChangeOrderStatusUseCasePort ChangeOrderStatusUseCase;

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
}
