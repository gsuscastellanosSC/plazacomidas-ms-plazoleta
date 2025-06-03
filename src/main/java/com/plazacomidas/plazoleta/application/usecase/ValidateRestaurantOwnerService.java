package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.port.in.ValidateRestaurantOwnerPort;
import com.plazacomidas.plazoleta.application.port.in.ValidateUserRolePort;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateRestaurantOwnerService implements ValidateRestaurantOwnerPort {

    private final ValidateUserRolePort userRoleValidator;

    @Override
    public void validate(Long ownerId) {

        userRoleValidator.validateUserHasRole(
                ownerId,
                RestaurantConstants.ROLE_OWNER,
                RestaurantConstants.MSG_USER_NOT_OWNER
        );
    }
}
