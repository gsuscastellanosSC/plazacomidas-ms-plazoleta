package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;

public interface CreateOrderUseCasePort {
    void createOrder(Long clientId, CreateOrderRequestDto request);
}
