package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import org.springframework.data.domain.Page;

public interface OrderQueryUseCasePort {

    Page<OrderResponseDto> findByStatusAndEmployeeRestaurant(OrderStatus status, Long empleadoId, int page, int size);
}
