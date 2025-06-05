package com.plazacomidas.plazoleta.domain.repository;

import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import com.plazacomidas.plazoleta.infrastructure.adapter.out.database.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface OrderRepository {
    void save(Order order);
    boolean existsActiveOrderByClientId(Long clientId);
    Page<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status, PageRequest pageRequest);
    Order getById(Long orderId);
    List<OrderEntity> findAllByRestaurantId(Long restaurantId);
}
