package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.application.dto.OrderAndEmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class MarkOrderReadyValidator {

    public void validate(OrderAndEmployeeDto dto) {
        MarkOrderReadyFieldValidator.ORDER_IS_NOT_NULL.validate(dto.getOrder());
        MarkOrderReadyFieldValidator.ROLE_IS_EMPLOYEE.validate(dto.getEmployee().getRole());
        MarkOrderReadyFieldValidator.ORDER_RESTAURANT_MATCHES
                .validate(Long.valueOf(dto.getEmployee().getRestaurantId()).equals(dto.getOrder().getRestaurantId()));
        MarkOrderReadyFieldValidator.ORDER_STATUS_IS_PREPARATION.validate(dto.getOrder());
    }
}
