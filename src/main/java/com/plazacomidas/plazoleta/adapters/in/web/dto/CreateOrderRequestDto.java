package com.plazacomidas.plazoleta.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class CreateOrderRequestDto {

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("id_chef")
    private Long chefId;

    @JsonProperty("dishes")
    private List<DishOrderDto> dishes;
}
