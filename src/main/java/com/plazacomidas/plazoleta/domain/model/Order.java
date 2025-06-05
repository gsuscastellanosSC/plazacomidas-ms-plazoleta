package com.plazacomidas.plazoleta.domain.model;

import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.time.LocalDateTime;

@Getter
@Builder
public class Order {

    private Long id;
    private Long clientId;
    private Long restaurantId;
    private Long assignedEmployeeId;
    private Long chefId;
    private LocalDateTime creationDate;
    private OrderStatus status;
    private List<OrderDish> dishes;
}
