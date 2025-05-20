package com.plazacomidas.plazoleta.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@RequiredArgsConstructor
public class UpdateDishRequestDto {

    @JsonProperty("precio")
    private Integer price;

    @JsonProperty("descripcion")
    private String description;
}
