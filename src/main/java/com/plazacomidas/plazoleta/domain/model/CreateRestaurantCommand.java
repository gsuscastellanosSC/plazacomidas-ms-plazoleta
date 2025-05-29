package com.plazacomidas.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantCommand {
    private Restaurant restaurant;
    private String token;
}
