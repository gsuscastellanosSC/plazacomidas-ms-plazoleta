package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.DishOrderDto;
import com.plazacomidas.plazoleta.application.port.in.ValidateUserRolePort;
import com.plazacomidas.plazoleta.application.port.out.OrderRepositoryPort;
import com.plazacomidas.plazoleta.common.OrderConstants;
import com.plazacomidas.plazoleta.domain.exception.BusinessException;
import com.plazacomidas.plazoleta.domain.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final OrderRepositoryPort orderRepository;
    private final ValidateUserRolePort userRoleValidator;

    public void validateCreateOrder(Long clientId, CreateOrderRequestDto dto) {

        if (orderRepository.existsActiveOrderByClientId(clientId)) {
            throw new BusinessException(OrderConstants.MSG_ALREADY_HAS_ACTIVE_ORDER);
        }

        userRoleValidator.validateUserHasRole(clientId, UserRole.CLIENTE.getRol(), OrderConstants.MSG_ONLY_CLIENT_CAN_CREATE_ORDER);

        OrderFieldValidator.CLIENT_ID.validate(clientId);
        OrderFieldValidator.RESTAURANT_ID.validate(dto.getRestaurantId());
        OrderFieldValidator.DISHES.validate(dto.getDishes());

        for (DishOrderDto dish : dto.getDishes()) {
            OrderFieldValidator.DISH_ID.validate(dish.getDishId());
            OrderFieldValidator.QUANTITY.validate(dish.getQuantity());
        }
    }
}
