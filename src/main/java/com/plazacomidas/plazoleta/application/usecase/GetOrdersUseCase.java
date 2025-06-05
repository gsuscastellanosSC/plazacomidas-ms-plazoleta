package com.plazacomidas.plazoleta.application.usecase;

import com.plazacomidas.plazoleta.adapters.in.web.dto.DishOrderDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.OrderResponseDto;
import com.plazacomidas.plazoleta.adapters.in.web.dto.UserResponseDto;
import com.plazacomidas.plazoleta.application.port.in.GetOrdersUseCasePort;
import com.plazacomidas.plazoleta.application.port.in.UserServicePort;
import com.plazacomidas.plazoleta.domain.exception.InvalidCredentialsException;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import com.plazacomidas.plazoleta.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetOrdersUseCase implements GetOrdersUseCasePort {
    private final OrderRepository orderRepository;
    private final UserServicePort userClient;

    @Override
    public Page<OrderResponseDto> getOrdersByStatusForEmployee(Long employeeId, String status, int page, int size) {
        UserResponseDto user = userClient.getUserById(employeeId);

        if (user == null || user.getRestaurantId() == null) {
            throw new InvalidCredentialsException("El empleado no tiene restaurante asignado.");
        }

        OrderStatus statusEnum;
        try {
            statusEnum = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + status);
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        return orderRepository
                .findByRestaurantIdAndStatus(Long.valueOf(user.getRestaurantId()), statusEnum, pageRequest)
                .map(this::mapToOrderResponseDto);
    }

    private OrderResponseDto mapToOrderResponseDto(Order order) {
        List<DishOrderDto> dishes = order.getDishes().stream()
                .map(dish -> DishOrderDto.builder()
                        .dishId(dish.getDishId())
                        .quantity(dish.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .id(order.getId())
                .clientId(order.getClientId())
                .restaurantId(order.getRestaurantId())
                .creationDate(order.getCreationDate())
                .status(order.getStatus().name())
                .dishes(dishes)
                .build();
    }
}
