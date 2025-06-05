package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;

import java.util.function.Predicate;

public enum AssignOrderFieldValidator {

    EMPLOYEE_ROLE_IS_VALID(
            value -> value != null && value instanceof String && ((String) value).equalsIgnoreCase("EMPLEADO"),
            "Solo un empleado puede asignarse pedidos"
    ),

    EMPLOYEE_HAS_RESTAURANT(
            value -> value != null,
            "El empleado no está asignado a ningún restaurante"
    ),

    ORDER_IS_NOT_NULL(
            value -> value != null,
            "El pedido no existe"
    ),

    ORDER_MATCHES_EMPLOYEE_RESTAURANT(
            value -> value instanceof Boolean && (Boolean) value,
            "El pedido no pertenece al restaurante del empleado"
    ),

    ORDER_STATUS_IS_PENDING(
            value -> value instanceof Order && ((Order) value).getStatus() == OrderStatus.PENDIENTE,
            "Solo pedidos en estado PENDIENTE pueden ser asignados"
    );

    private final Predicate<Object> predicate;
    private final String errorMessage;

    AssignOrderFieldValidator(Predicate<Object> predicate, String errorMessage) {
        this.predicate = predicate;
        this.errorMessage = errorMessage;
    }

    public void validate(Object value) {
        if (!predicate.test(value)) {
            throw new BusinessException(errorMessage);
        }
    }
}
