package com.plazacomidas.plazoleta.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishOrderDto {

    @JsonProperty("dishId")
    private Long dishId;

    @JsonProperty("quantity")
    private int quantity;
}
