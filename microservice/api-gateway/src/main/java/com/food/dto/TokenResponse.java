package com.food.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {

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
