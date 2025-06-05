package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.application.dto.RegisterTraceabilityDto;

public interface RegisterOrderTraceUseCasePort {
    void registerTrace(RegisterTraceabilityDto registerTraceabilityDto);
}
