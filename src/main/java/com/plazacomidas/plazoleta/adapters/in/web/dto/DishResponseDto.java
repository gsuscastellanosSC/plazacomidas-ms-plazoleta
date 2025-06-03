package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DishResponseDto {
    private String nombre;
    private String descripcion;
    private Integer precio;
}
