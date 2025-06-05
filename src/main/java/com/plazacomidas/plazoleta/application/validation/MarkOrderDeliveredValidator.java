package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.application.dto.OrderAndEmployeeDto;
import com.plazacomidas.plazoleta.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarkOrderDeliveredValidator {

    public void validate(OrderAndEmployeeDto dto, String pin) {
        Order order = dto.getOrder();

        MarkOrderDeliveredFieldValidator.ORDER_IS_NOT_NULL.validate(order);
        MarkOrderDeliveredFieldValidator.ORDER_STATUS_IS_READY.validate(order);
        MarkOrderDeliveredFieldValidator.ORDER_IS_NOT_ALREADY_DELIVERED.validate(order);
        //MarkOrderDeliveredFieldValidator.PIN_IS_VALID.validate(pin.equals(order.getPin()));
    }
}
