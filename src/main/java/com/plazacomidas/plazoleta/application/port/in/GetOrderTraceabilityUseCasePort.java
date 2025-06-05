package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.adapters.in.web.dto.TraceabilityResponseDto;

import java.util.List;

public interface GetOrderTraceabilityUseCasePort {
    List<TraceabilityResponseDto> getTraceabilityForOrder(Long orderId, Long clientId);
}
