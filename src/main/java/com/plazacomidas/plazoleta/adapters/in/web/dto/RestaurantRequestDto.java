package com.plazacomidas.plazoleta.adapters.in.web.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequestDto {
        private String nombre;
        private String nit;
        private String direccion;
        private String telefono;
        private String urlLogo;
        private Long idPropietario;
}
