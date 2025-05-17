package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import com.plazacomidas.plazoleta.domain.exception.RestaurantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidateRestaurantOwnerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ValidateRestaurantOwnerService service;

    private static final Long OWNER_ID = 123L;
    private static final String EXPECTED_URL = RestaurantConstants.USER_SERVICE_URL + OWNER_ID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidate_OwnerIsValid_NoExceptionThrown() {
        UserResponseDto response = new UserResponseDto();
        response.setRole(RestaurantConstants.ROLE_OWNER);

        when(restTemplate.getForObject(EXPECTED_URL, UserResponseDto.class)).thenReturn(response);

        assertDoesNotThrow(() -> service.validate(OWNER_ID));
        verify(restTemplate).getForObject(EXPECTED_URL, UserResponseDto.class);
    }

    @Test
    void testValidate_UserIsNull_ThrowsRestaurantException() {
        when(restTemplate.getForObject(EXPECTED_URL, UserResponseDto.class)).thenReturn(null);

        RestaurantException exception = assertThrows(RestaurantException.class, () ->
                service.validate(OWNER_ID));

        assertEquals(RestaurantConstants.MSG_USER_VALIDATION_FAILED, exception.getMessage());
    }

    @Test
    void testValidate_UserHasNoRole_ThrowsRestaurantException() {
        UserResponseDto response = new UserResponseDto();
        response.setRole(null);

        when(restTemplate.getForObject(EXPECTED_URL, UserResponseDto.class)).thenReturn(response);

        RestaurantException exception = assertThrows(RestaurantException.class, () ->
                service.validate(OWNER_ID));

        assertEquals(RestaurantConstants.MSG_USER_VALIDATION_FAILED, exception.getMessage());
    }

    @Test
    void testValidate_UserIsNotOwner_ThrowsRestaurantException() {
        UserResponseDto response = new UserResponseDto();
        response.setRole("cliente");

        when(restTemplate.getForObject(EXPECTED_URL, UserResponseDto.class)).thenReturn(response);

        RestaurantException exception = assertThrows(RestaurantException.class, () ->
                service.validate(OWNER_ID));

        assertEquals(RestaurantConstants.MSG_USER_NOT_OWNER, exception.getMessage());
    }
}
