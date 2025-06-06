package com.plazacomidas.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantModel {
    private Long id;
    private String nombre;
    private String urlLogo;
}
