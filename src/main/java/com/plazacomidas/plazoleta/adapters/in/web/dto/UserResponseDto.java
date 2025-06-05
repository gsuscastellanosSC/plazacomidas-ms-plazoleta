package com.plazacomidas.plazoleta.adapters.in.web.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.plazacomidas.plazoleta.common.RestaurantConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;

    @JsonProperty(RestaurantConstants.JSON_FIRST_NAME)
    private String firstName;

    @JsonProperty(RestaurantConstants.JSON_LAST_NAME)
    private String lastName;

    @JsonProperty(RestaurantConstants.JSON_EMAIL)
    private String email;

    @JsonProperty(RestaurantConstants.JSON_ROLE)
    private String role;

    @JsonProperty(RestaurantConstants.JSON_RESTAURANT_ID)
    private String restaurantId;
}
