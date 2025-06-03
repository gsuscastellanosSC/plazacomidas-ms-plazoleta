package com.plazacomidas.plazoleta.application.port.in;

public interface ValidateUserRolePort {
    void validateUserHasRole(Long userId, String expectedRole, String errorMessageIfInvalid);
}
