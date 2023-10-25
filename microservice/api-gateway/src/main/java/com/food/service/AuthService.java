package com.food.service;

import com.food.dto.UserDto;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public interface AuthService {
    AccessTokenResponse authenticate(UserDto dto) throws Exception;

    AccessTokenResponse refreshToken(String token);

    UserRepresentation getUser(String username);

    List<RoleRepresentation> getRoles();

    List<GroupRepresentation> getGroup();
}
