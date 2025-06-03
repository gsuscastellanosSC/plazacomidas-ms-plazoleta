package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.application.port.in.CreateOrderUseCasePort;
import com.plazacomidas.plazoleta.application.validation.OrderValidator;
import com.plazacomidas.plazoleta.domain.factory.OrderFactory;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateOrderUseCase implements CreateOrderUseCasePort {

    private final OrderValidator orderValidator;
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;

    @Override
    public void createOrder(Long clientId, CreateOrderRequestDto request) {
        orderValidator.validateCreateOrder(clientId, request);
        Order order = orderFactory.create(clientId, request);
        orderRepository.save(order);
    }
}
