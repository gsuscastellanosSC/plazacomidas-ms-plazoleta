package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.application.dto.OrderAndEmployeeDto;

public interface OrderAndEmployeeFetcherPort {
    OrderAndEmployeeDto fetch(Long orderId, Long employeeId);
}
