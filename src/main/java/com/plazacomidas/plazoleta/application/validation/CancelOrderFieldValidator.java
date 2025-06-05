package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;

import java.util.function.Predicate;

public enum CancelOrderFieldValidator {
    ORDER_IS_NOT_NULL(
            value -> value != null,
            "El pedido no existe"
    ),
    ORDER_STATUS_IS_PENDING(
            value -> OrderStatus.PENDIENTE.equals(((Order) value).getStatus()),
            "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse"
    ),
    ORDER_BELONGS_TO_CLIENT(
            value -> (boolean) value,
            "El pedido no pertenece al cliente"
    );

    private final Predicate<Object> rule;
    private final String message;

    CancelOrderFieldValidator(Predicate<Object> rule, String message) {
        this.rule = rule;
        this.message = message;
    }

    public void validate(Object value) {
        if (!rule.test(value)) {
            throw new BusinessException(message);
        }
    }
}
