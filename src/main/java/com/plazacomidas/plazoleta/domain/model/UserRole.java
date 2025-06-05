package com.plazacomidas.plazoleta.domain.model;

import lombok.Getter;

@Getter
public enum UserRole {
    CLIENTE("CLIENTE"),
    PROPIETARIO("PROPIETARIO"),
    ADMINISTRADOR("ADMINISTRADOR"),
    EMPLEADO("EMPLEADO");

    private final String rol;

    UserRole(String rol) {
        this.rol = rol;
    }
}
