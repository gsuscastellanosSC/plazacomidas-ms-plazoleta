package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DishRequestDto {

    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private Long idCategoria;
    private Long restauranteId;

}
