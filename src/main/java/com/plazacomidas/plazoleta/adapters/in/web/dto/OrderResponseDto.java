package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponseDto {

    private Long id;
    private Long clientId;
    private Long restaurantId;
    private LocalDateTime creationDate;
    private String status;
    private List<DishOrderDto> dishes;

}
