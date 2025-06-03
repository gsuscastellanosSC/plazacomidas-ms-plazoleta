package com.plazacomidas.plazoleta.adapters.in.web.controller;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.application.port.in.CreateOrderUseCasePort;
import com.plazacomidas.plazoleta.common.OrderConstants;
import com.plazacomidas.plazoleta.common.SecurityExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OrderConstants.API_ORDERS)
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCasePort createOrderUseCase;

    @PostMapping
    @PreAuthorize(SecurityExpressions.HAS_ROLE_CLIENTE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader("client-id") Long clientId,
                            @RequestBody CreateOrderRequestDto request) {
        createOrderUseCase.createOrder(clientId, request);
    }
}
