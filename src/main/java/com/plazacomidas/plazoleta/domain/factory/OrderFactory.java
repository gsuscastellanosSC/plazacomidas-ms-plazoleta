package com.plazacomidas.plazoleta.domain.factory;
import com.plazacomidas.plazoleta.adapters.in.web.dto.CreateOrderRequestDto;
import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderDish;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderFactory {

    public Order create(Long clientId, CreateOrderRequestDto request) {
        List<OrderDish> dishes = request.getDishes().stream()
                .map(d -> OrderDish.builder()
                        .dishId(d.getDishId())
                        .quantity(d.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return Order.builder()
                .clientId(clientId)
                .restaurantId(request.getRestaurantId())
                .chefId(request.getChefId())
                .creationDate(LocalDateTime.now())
                .status(OrderStatus.PENDIENTE)
                .dishes(dishes)
                .build();
    }
}
