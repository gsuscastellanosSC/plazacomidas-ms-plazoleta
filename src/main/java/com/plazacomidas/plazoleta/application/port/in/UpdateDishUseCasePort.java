package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UpdateDishRequestDto;

public interface UpdateDishUseCasePort {
    void execute(Long dishId, Long userId, UpdateDishRequestDto dto);
}
