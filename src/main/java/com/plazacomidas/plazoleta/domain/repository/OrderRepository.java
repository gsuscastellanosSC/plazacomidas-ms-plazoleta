package com.plazacomidas.plazoleta.domain.repository;

import com.plazacomidas.plazoleta.domain.model.Order;
import com.plazacomidas.plazoleta.domain.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderRepository {
    void save(Order order);
    boolean existsActiveOrderByClientId(Long clientId);
    Page<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status, PageRequest pageRequest);
}
