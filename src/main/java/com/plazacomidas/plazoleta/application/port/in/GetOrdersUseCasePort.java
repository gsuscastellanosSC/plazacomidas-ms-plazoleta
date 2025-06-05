package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import org.springframework.data.domain.Page;

public interface GetOrdersUseCasePort {

    Page<OrderResponseDto> getOrdersByStatusForEmployee(Long employeeId, String status, int page, int size);
}
