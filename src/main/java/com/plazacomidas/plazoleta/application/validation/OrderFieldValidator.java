package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.common.OrderConstants;
import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.exception.InvalidOrderException;

import java.util.List;
import java.util.Objects;

public enum OrderFieldValidator {

    CLIENT_ID {
        @Override
        public void validate(Object value) {
            if (Objects.isNull(value)) {
                throw new InvalidOrderException(OrderConstants.MSG_REQUIRED_CLIENT_ID);
            }
        }
    },
    RESTAURANT_ID {
        @Override
        public void validate(Object value) {
            if (Objects.isNull(value)) {
                throw new InvalidOrderException(OrderConstants.MSG_REQUIRED_RESTAURANT);
            }
        }
    },
    DISHES {
        @Override
        public void validate(Object value) {
            if (value == null || !(value instanceof List<?> list) || list.isEmpty()) {
                throw new InvalidOrderException(OrderConstants.MSG_REQUIRED_DISHES);
            }
        }
    },
    DISH_ID {
        @Override
        public void validate(Object value) {
            if (Objects.isNull(value)) {
                throw new InvalidOrderException(OrderConstants.MSG_REQUIRED_DISHES);
            }
        }
    },
    QUANTITY {
        @Override
        public void validate(Object value) {
            if (!(value instanceof Integer quantity) || quantity <= 0) {
                throw new InvalidOrderException(OrderConstants.MSG_INVALID_QUANTITY);
            }
        }
    };

    public abstract void validate(Object value);
}
