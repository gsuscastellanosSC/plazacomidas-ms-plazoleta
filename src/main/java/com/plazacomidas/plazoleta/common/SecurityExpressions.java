package com.plazacomidas.plazoleta.common;

public class SecurityExpressions {

    private SecurityExpressions() {}

    public static final String HAS_ROLE_CLIENTE = "hasRole('CLIENTE')";
    public static final String HAS_ROLE_PROPIETARIO = "hasRole('PROPIETARIO')";
    public static final String HAS_ROLE_ADMIN = "hasRole('ADMINISTRADOR')";
    public static final String HAS_ROLE_EMPLOYEE = "hasRole('EMPLEADO')";
}
