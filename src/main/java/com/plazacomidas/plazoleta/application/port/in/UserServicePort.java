package com.plazacomidas.plazoleta.application.port.in;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;

public interface UserServicePort {
    UserResponseDto getUserById(Long userId);
}
