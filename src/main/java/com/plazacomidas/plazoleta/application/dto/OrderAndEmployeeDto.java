package com.plazacomidas.plazoleta.application.dto;

import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderAndEmployeeDto {
    private final Order order;
    private final UserResponseDto employee;
}
