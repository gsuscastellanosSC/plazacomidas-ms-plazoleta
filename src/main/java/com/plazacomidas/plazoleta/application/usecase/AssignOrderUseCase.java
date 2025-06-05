package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.application.port.in.AssignOrderUseCasePort;
import com.plazacomidas.plazoleta.application.validation.AssignOrderValidator;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignOrderUseCase implements AssignOrderUseCasePort {

    private final OrderRepository orderRepository;
    private final AssignOrderValidator validator;

    @Override
    public void assignOrder(Long orderId, Long employeeId) {
        Order order = validator.validate(orderId, employeeId);

        Order updatedOrder = Order.builder()
                .id(order.getId())
                .clientId(order.getClientId())
                .restaurantId(order.getRestaurantId())
                .assignedEmployeeId(employeeId)
                .chefId(order.getChefId())
                .creationDate(order.getCreationDate())
                .status(OrderStatus.EN_PREPARACION)
                .dishes(order.getDishes())
                .build();

        orderRepository.save(updatedOrder);
    }
}
