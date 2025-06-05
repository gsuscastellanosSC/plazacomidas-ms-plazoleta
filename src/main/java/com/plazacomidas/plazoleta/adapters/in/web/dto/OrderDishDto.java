package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDishDto {
    private Long dishId;
    private int quantity;
}
