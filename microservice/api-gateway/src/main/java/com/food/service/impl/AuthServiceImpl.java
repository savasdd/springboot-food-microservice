package com.food.service.impl;

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
    public AccessTokenResponse authenticate(UserDto dto) throws GeneralException {
        AccessTokenResponse response = client.authenticate(dto);
        return response;
    }

    @Override
    public AccessTokenResponse refreshToken(String token) throws GeneralException {
        AccessTokenResponse response = client.refreshToken(token);
        return response;
    }



}
