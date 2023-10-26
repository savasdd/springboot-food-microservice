package com.food.service;

import com.food.dto.TokenResponse;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public interface AuthService {
    TokenResponse authenticate(UserDto dto) throws GeneralException;

    TokenResponse refreshToken(String token) throws GeneralException;

}
