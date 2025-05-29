package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.port.in.ValidateRestaurantOwnerPort;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.domain.exception.RestaurantException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;


@Service
@RequiredArgsConstructor
public class ValidateRestaurantOwnerService implements ValidateRestaurantOwnerPort {

    private final RestTemplate restTemplate;

    @Override
    public void validate(Long ownerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidCredentialsException();
        }

        String token = (String) authentication.getCredentials();

        if (token == null || token.isBlank()) {
            throw new InvalidCredentialsException();
        }

        String url = RestaurantConstants.USER_SERVICE_URL + ownerId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UserResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserResponseDto.class
        );

        UserResponseDto user = response.getBody();

        if (user == null || user.getRole() == null) {
            throw new RestaurantException(RestaurantConstants.MSG_USER_VALIDATION_FAILED);
        }

        if (!RestaurantConstants.ROLE_OWNER.equalsIgnoreCase(user.getRole())) {
            throw new RestaurantException(RestaurantConstants.MSG_USER_NOT_OWNER);
        }
    }
}

