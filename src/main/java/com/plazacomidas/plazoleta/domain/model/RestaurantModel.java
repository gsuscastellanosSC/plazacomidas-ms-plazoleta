package com.plazacomidas.plazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantModel {
    private Long id;
    private String nombre;
    private String urlLogo;
}
