package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;

import java.util.function.Predicate;

public enum MarkOrderReadyFieldValidator {

    ROLE_IS_EMPLOYEE(role -> "EMPLEADO".equalsIgnoreCase((String) role),
            "Solo un empleado puede marcar el pedido como listo"),

    ORDER_RESTAURANT_MATCHES(empRestEquals -> (Boolean) empRestEquals,
            "El pedido no pertenece al restaurante del empleado"),

    ORDER_IS_NOT_NULL(order -> order != null,
            "El pedido no existe"),

    ORDER_STATUS_IS_PREPARATION(order -> order instanceof Order &&
            OrderStatus.EN_PREPARACION.equals(((Order) order).getStatus()),
            "El pedido debe estar en estado EN_PREPARACION para ser marcado como LISTO");

    private final Predicate<Object> predicate;
    private final String errorMessage;

    MarkOrderReadyFieldValidator(Predicate<Object> predicate, String errorMessage) {
        this.predicate = predicate;
        this.errorMessage = errorMessage;
    }

    public void validate(Object value) {
        if (!predicate.test(value)) {
            throw new BusinessException(errorMessage);
        }
    }
}
