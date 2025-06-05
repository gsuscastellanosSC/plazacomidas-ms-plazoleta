package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.DeliverOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.TraceabilityResponseDto;
import com.plazacomidas.plazoleta.application.port.in.*;
import com.plazacomidas.plazoleta.common.OrderConstants;
import com.plazacomidas.plazoleta.common.SecurityExpressions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pedidos", description = "Operaciones para gestionar pedidos en la plazoleta de comidas")
public class OrderController {

    private final AssignOrderUseCasePort assignOrderUseCasePort;
    private final CreateOrderUseCasePort createOrderUseCase;
    private final GetOrdersUseCasePort getOrdersUseCase;
    private final ChangeOrderStatusUseCasePort ChangeOrderStatusUseCase;
    private final GetOrderTraceabilityUseCasePort getTraceabilityUseCase;

    @Operation(summary = "Crear pedido", description = "Permite al cliente crear un nuevo pedido con platos.")
    @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")
    @PostMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(
            @Parameter(description = "ID del cliente", required = true) @RequestHeader("client-id") Long clientId,
            @Parameter(description = "Detalles del pedido", required = true) @RequestBody CreateOrderRequestDto request) {
        createOrderUseCase.createOrder(clientId, request);
    }

    @Operation(summary = "Listar pedidos por estado", description = "Permite al empleado ver los pedidos filtrados por estado.")
    @ApiResponse(responseCode = "200", description = "Listado de pedidos obtenido correctamente")
    @GetMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    public Page<OrderResponseDto> getOrdersByStatus(
            @Parameter(description = "ID del empleado", required = true) @RequestHeader("employee-id") Long employeeId,
            @Parameter(description = "Estado del pedido", required = true) @RequestParam("status") String status,
            @Parameter(description = "Página") @RequestParam("page") int page,
            @Parameter(description = "Tamaño de página") @RequestParam("size") int size) {
        return getOrdersUseCase.getOrdersByStatusForEmployee(employeeId, status, page, size);
    }

    @Operation(summary = "Asignarse un pedido", description = "Permite a un empleado asignarse un pedido pendiente.")
    @ApiResponse(responseCode = "200", description = "Pedido asignado exitosamente")
    @GetMapping("/{orderId}/assign")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    public ResponseEntity<Void> assignOrderToEmployee(
            @Parameter(description = "ID del pedido") @PathVariable Long orderId,
            @Parameter(description = "ID del empleado") @RequestHeader("employee-id") Long employeeId) {
        assignOrderUseCasePort.assignOrder(orderId, employeeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Marcar pedido como listo", description = "Permite al empleado marcar un pedido como listo para entrega.")
    @ApiResponse(responseCode = "200", description = "Pedido marcado como listo")
    @PostMapping("/{orderId}/ready")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    public ResponseEntity<Void> markOrderAsReady(
            @Parameter(description = "ID del pedido") @PathVariable Long orderId,
            @Parameter(description = "ID del empleado") @RequestHeader("employee-id") Long employeeId) {
        ChangeOrderStatusUseCase.markOrderAsReady(orderId, employeeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Entregar pedido", description = "Permite al empleado entregar el pedido con validación del PIN.")
    @ApiResponse(responseCode = "200", description = "Pedido entregado exitosamente")
    @PatchMapping("/{orderId}/deliver")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_EMPLOYEE)
    public ResponseEntity<Void> deliverOrder(
            @Parameter(description = "ID del pedido") @PathVariable Long orderId,
            @Parameter(description = "ID del empleado") @RequestHeader("employee-id") Long employeeId,
            @Parameter(description = "PIN de seguridad") @RequestBody DeliverOrderRequestDto requestDto) {
        ChangeOrderStatusUseCase.markOrderAsDelivered(orderId, employeeId, requestDto.getPin());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancelar pedido", description = "Permite al cliente cancelar su pedido si aún no ha sido preparado.")
    @ApiResponse(responseCode = "200", description = "Pedido cancelado")
    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    public ResponseEntity<Void> cancelOrder(
            @Parameter(description = "ID del pedido") @PathVariable Long orderId,
            @Parameter(description = "ID del cliente") @RequestHeader("client-id") Long clientId) {
        ChangeOrderStatusUseCase.cancelOrder(orderId, clientId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Consultar trazabilidad", description = "Permite al cliente ver el historial de cambios de estado de su pedido.")
    @ApiResponse(responseCode = "200", description = "Historial de trazabilidad obtenido")
    @GetMapping("/{orderId}/trazabilidad")
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    public ResponseEntity<List<TraceabilityResponseDto>> getOrderTraceability(
            @Parameter(description = "ID del cliente") @RequestHeader("client-id") Long clientId,
            @Parameter(description = "ID del pedido") @PathVariable Long orderId) {

        List<TraceabilityResponseDto> logs = getTraceabilityUseCase.getTraceabilityForOrder(orderId, clientId);
        return ResponseEntity.ok(logs);
    }
}
