package com.plazacomidas.plazoleta.application.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterTraceabilityDto {
    private final Long orderId;
    private final String previousStatus;
    private final String newStatus;
    private final Long clientId;
    private final String clientEmail;
    private final Long employeeId;
    private final String employeeEmail;
}
