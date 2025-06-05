package com.plazacomidas.plazoleta.adapters.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
public class UpdateDishRequestDto {

    @JsonProperty("precio")
    private Integer price;

    @JsonProperty("descripcion")
    private String description;

}
