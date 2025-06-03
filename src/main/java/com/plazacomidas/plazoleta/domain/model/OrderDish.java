package com.plazacomidas.plazoleta.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDish {
    private Long dishId;
    private int quantity;
}
