package com.plazacomidas.plazoleta.application.validation;

import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.port.in.UserServicePort;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignOrderValidator {

    private final OrderRepository orderRepository;
    private final UserServicePort userClient;

    public Order validate(Long orderId, Long employeeId) {
        UserResponseDto user = userClient.getUserById(employeeId);

        AssignOrderFieldValidator.EMPLOYEE_ROLE_IS_VALID.validate(user.getRole());
        AssignOrderFieldValidator.EMPLOYEE_HAS_RESTAURANT.validate(user.getRestaurantId());

        Order order = orderRepository.getById(orderId);
        AssignOrderFieldValidator.ORDER_IS_NOT_NULL.validate(order);
         AssignOrderFieldValidator.ORDER_MATCHES_EMPLOYEE_RESTAURANT.validate(
                user.getRestaurantId() != null &&
                        order.getRestaurantId() != null &&
                        Long.valueOf(user.getRestaurantId()).equals(order.getRestaurantId())
        );
        AssignOrderFieldValidator.ORDER_STATUS_IS_PENDING.validate(order);

        return order;
    }
}
