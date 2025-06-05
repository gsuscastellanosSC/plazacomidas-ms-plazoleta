package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.dto.OrderAndEmployeeDto;
import com.plazacomidas.plazoleta.application.port.in.OrderAndEmployeeFetcherPort;
import com.plazacomidas.plazoleta.application.port.in.UserServicePort;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAndEmployeeFetcherUseCase implements OrderAndEmployeeFetcherPort {

    private final UserServicePort userClient;
    private final OrderRepository orderRepository;

    public OrderAndEmployeeDto fetch(Long orderId, Long employeeId) {
        return OrderAndEmployeeDto.builder()
                .order(orderRepository.getById(orderId))
                .employee(userClient.getUserById(employeeId))
                .build();
    }
}
