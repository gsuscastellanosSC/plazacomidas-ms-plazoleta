package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;

import java.util.function.Predicate;

public enum MarkOrderDeliveredFieldValidator {
    ORDER_IS_NOT_NULL(order -> order != null, "El pedido no existe"),
    ORDER_STATUS_IS_READY(order -> ((Order) order).getStatus() == OrderStatus.LISTO, "Solo se pueden entregar pedidos en estado LISTO"),
    ORDER_IS_NOT_ALREADY_DELIVERED(order -> ((Order) order).getStatus() != OrderStatus.ENTREGADO, "El pedido ya fue entregado"),
    PIN_IS_VALID(valid -> (Boolean) valid, "El PIN de seguridad es incorrecto");

    private final Predicate<Object> rule;
    private final String message;

    MarkOrderDeliveredFieldValidator(Predicate<Object> rule, String message) {
        this.rule = rule;
        this.message = message;
    }

    public void validate(Object value) {
        if (!rule.test(value)) {
            throw new BusinessException(message);
        }
    }
}
