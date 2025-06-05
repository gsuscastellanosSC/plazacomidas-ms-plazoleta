package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderEfficiencyResponseDto;

public interface GetOrderEfficiencyUseCasePort {
    OrderEfficiencyResponseDto getEfficiency(Long restaurantId, Long ownerId);
}
