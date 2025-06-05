package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;
    private Long clientId;
    private Long restaurantId;
    private LocalDateTime creationDate;
    private String status;
    private List<DishOrderDto> dishes;

}
