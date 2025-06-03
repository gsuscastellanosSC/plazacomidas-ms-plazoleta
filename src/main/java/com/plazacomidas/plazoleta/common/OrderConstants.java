package com.plazacomidas.plazoleta.common;

public class OrderConstants {
    private OrderConstants() {}

    public static final String API_ORDERS = "/orders";

    public static final String MSG_REQUIRED_RESTAURANT = "El restaurante es obligatorio";
    public static final String MSG_REQUIRED_DISHES = "Debe agregar al menos un plato al pedido";
    public static final String MSG_INVALID_QUANTITY = "La cantidad de platos debe ser un número positivo y mayor a cero";
    public static final String MSG_ALREADY_HAS_ACTIVE_ORDER = "Ya tiene un pedido en proceso";
    public static final String MSG_REQUIRED_CLIENT_ID = "El ID del cliente es obligatorio";

    public static final String MSG_ONLY_CLIENT_CAN_CREATE_ORDER = "Solo los usuarios con rol CLIENTE pueden crear pedidos";

}
