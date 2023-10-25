package com.food.service.impl;

import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public UserRepresentation getUser(String username) throws GeneralException {
        UserRepresentation userList = client.initClient().users().search(username.trim()).get(0);
        return userList;
    }

    @Override
    public List<RoleRepresentation> getRoles() throws GeneralException {
        List<RoleRepresentation> list = client.initClient().roles().list();
        return list;
    }

    @Override
    public List<GroupRepresentation> getGroup() throws GeneralException {
        List<GroupRepresentation> list = client.initClient().groups().groups();
        return list;
    }


}
