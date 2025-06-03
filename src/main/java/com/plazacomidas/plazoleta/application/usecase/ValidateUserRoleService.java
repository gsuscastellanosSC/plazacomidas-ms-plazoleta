package com.plazacomidas.plazoleta.application.usecase;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.port.in.ValidateUserRolePort;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.rest.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ValidateUserRoleService implements ValidateUserRolePort {

    private final UserClient userClient;

    @Override
    public void validateUserHasRole(Long userId, String expectedRole, String errorMessageIfInvalid) {
        UserResponseDto user = userClient.getUserById(userId);

        if (user == null || user.getRole() == null || !expectedRole.equalsIgnoreCase(user.getRole())) {
            throw new InvalidCredentialsException(errorMessageIfInvalid);
        }
    }
}
