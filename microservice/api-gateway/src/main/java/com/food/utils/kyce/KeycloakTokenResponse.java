package com.food.utils.kyce;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.dto.UserRolDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KeycloakTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("roles")
    private List<UserRolDto> roles;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in_refresh")
    private Long expiresInRefresh;

}
