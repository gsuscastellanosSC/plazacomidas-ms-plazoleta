package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderEfficiencyDto {
    private final Long orderId;
    private final Long totalTimeMinutes;
}
