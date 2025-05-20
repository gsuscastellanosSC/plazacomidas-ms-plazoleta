package com.plazacomidas.plazoleta.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class Dish {
    private Long id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String urlImagen;
    private Long idCategoria;
    private Long restauranteId;
    private boolean activo;
}
