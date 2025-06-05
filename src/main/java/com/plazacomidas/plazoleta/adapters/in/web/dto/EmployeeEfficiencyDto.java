package com.plazacomidas.plazoleta.adapters.in.web.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeEfficiencyDto {
    private final Long employeeId;
    private final String employeeName;
    private final Double averageTimeMinutes;
}
