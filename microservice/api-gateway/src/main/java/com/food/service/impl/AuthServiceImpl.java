package com.food.service.impl;

import com.food.dto.TokenResponse;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final KeycloakClient client;

    public AuthServiceImpl(KeycloakClient client) {
        this.client = client;
    }


    @Override
    public TokenResponse authenticate(UserDto dto) throws GeneralException {
        if (dto.getUsername() == null || dto.getPassword() == null)
            throw new GeneralException("Invalid username or password!");

        AccessTokenResponse response = client.authenticate(dto);
        return TokenResponse.builder().accessToken(response.getToken()).expiresIn(response.getExpiresIn()).tokenType(response.getTokenType()).refreshToken(response.getRefreshToken()).expiresInRefresh(response.getRefreshExpiresIn()).build();
    }

    @Override
    public TokenResponse refreshToken(String token) throws GeneralException {
        AccessTokenResponse response = client.refreshToken(token);
        return TokenResponse.builder().accessToken(response.getToken()).expiresIn(response.getExpiresIn()).tokenType(response.getTokenType()).refreshToken(response.getRefreshToken()).expiresInRefresh(response.getRefreshExpiresIn()).build();
    }


}
