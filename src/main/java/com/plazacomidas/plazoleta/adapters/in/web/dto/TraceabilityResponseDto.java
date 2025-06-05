package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TraceabilityResponseDto {
    private final LocalDateTime date;
    private final String previousStatus;
    private final String newStatus;
    private final String employeeEmail;
}
