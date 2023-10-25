package com.food.service;

import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public interface AuthService {
    AccessTokenResponse authenticate(UserDto dto) throws GeneralException;

    AccessTokenResponse refreshToken(String token) throws GeneralException;

    UserRepresentation getUser(String username) throws GeneralException;

    List<RoleRepresentation> getRoles() throws GeneralException;

    List<GroupRepresentation> getGroup() throws GeneralException;
}
