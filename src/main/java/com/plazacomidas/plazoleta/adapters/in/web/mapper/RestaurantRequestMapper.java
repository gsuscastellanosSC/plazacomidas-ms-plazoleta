package com.plazacomidas.plazoleta.adapters.in.web.mapper;

import com.plazacomidas.plazoleta.adapters.in.web.dto.RestaurantRequestDto;
import com.plazacomidas.plazoleta.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantRequestMapper {

    public Restaurant toDomain(RestaurantRequestDto dto) {
        return Restaurant.builder()
                .nombre(dto.getNombre())
                .nit(dto.getNit())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .urlLogo(dto.getUrlLogo())
                .idPropietario(dto.getIdPropietario())
                .build();
    }
}
