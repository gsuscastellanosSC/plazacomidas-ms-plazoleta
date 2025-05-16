package com.plazacomidas.plazoleta.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
    private int statusCode;
    private String errorCode;
    private String description;
}
