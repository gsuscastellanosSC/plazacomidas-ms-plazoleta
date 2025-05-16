package com.plazacomidas.plazoleta.common;

public class RestaurantConstants {

    public static final String API_RESTAURANTS = "/restaurantes";
    public static final String POST_CREATE_RESTAURANT = "/create";



    // ==== Validation Messages ====
    public static final String MSG_NAME_ONLY_NUMBERS = "El nombre no puede contener solo números";
    public static final String MSG_NIT_NUMERIC = "El NIT debe ser numérico";
    public static final String MSG_PHONE_FORMAT = "El nombre del restaurante no puede estar compuesto solo por números.";
    public static final String MSG_USER_NOT_OWNER = "El usuario no tiene rol de propietario.";
    public static final String MSG_USER_VALIDATION_FAILED = "No se pudo validar el usuario.";
    public static final String MSG_INVALID_ADDRESS = "La dirección no debe estar vacía.";
    public static final String MSG_INVALID_LOGO_URL = "La URL del logo no debe estar vacía.";
    public static final String MSG_MISSING_FIELDS = "Faltan campos obligatorios para la creación del restaurante.";

    public static final String JSON_FIRST_NAME = "nombre";
    public static final String JSON_LAST_NAME = "apellido";
    public static final String JSON_EMAIL = "correo";
    public static final String JSON_ROLE = "rol";

    public static final String TABLE_RESTAURANTS = "restaurantes";

    // ==== Column Names ====
    public static final String COL_ID = "id";
    public static final String COL_NAME = "nombre";
    public static final String COL_NIT = "nit";
    public static final String COL_ADDRESS = "direccion";
    public static final String COL_PHONE = "telefono";
    public static final String COL_LOGO_URL = "urllogo";
    public static final String COL_OWNER_ID = "id_propietario";

    public static final String USER_SERVICE_URL = "http://localhost:8081/plazacomidas-user/v1/usuarios/";
    public static final String USER_ROLE_KEY = "id_rol";
    public static final String ROLE_OWNER = "PROPIETARIO";

    private RestaurantConstants() {
        // Utility class
    }
}
