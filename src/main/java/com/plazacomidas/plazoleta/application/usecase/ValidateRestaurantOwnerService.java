package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.port.in.ValidateRestaurantOwnerPort;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.domain.exception.RestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ValidateRestaurantOwnerService implements ValidateRestaurantOwnerPort {

    private final RestTemplate restTemplate;

    @Override
    public void validate(Long ownerId) {
        String url = RestaurantConstants.USER_SERVICE_URL.concat(String.valueOf(ownerId));

        UserResponseDto user = restTemplate.getForObject(url, UserResponseDto.class);

        if (user == null || user.getRole() == null) {
            throw new RestaurantException(RestaurantConstants.MSG_USER_VALIDATION_FAILED);
        }

        if (!RestaurantConstants.ROLE_OWNER.equalsIgnoreCase(user.getRole())) {
            throw new RestaurantException(RestaurantConstants.MSG_USER_NOT_OWNER);
        }
    }
}
