package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderEfficiencyResponseDto {
    private final List<OrderEfficiencyDto> orderEfficiency;
    private final List<EmployeeEfficiencyDto> employeeRanking;
}
