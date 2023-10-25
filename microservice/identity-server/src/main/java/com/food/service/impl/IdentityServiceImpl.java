package com.food.service.impl;

import com.food.dto.UserDto;
import com.food.keycloak.KeycloakClient;
import com.food.service.IdentityService;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentityServiceImpl implements IdentityService {

    private final KeycloakClient client;

    public IdentityServiceImpl(KeycloakClient client) {
        this.client = client;
    }

    @Override
    public AccessTokenResponse authenticate(UserDto dto) throws Exception {
        AccessTokenResponse response = client.authenticate(dto);
        return response;
    }

    @Override
    public AccessTokenResponse refreshToken(String token) {
        AccessTokenResponse response = client.refreshToken(token);
        return response;
    }

    @Override
    public UserRepresentation getUser(String username) {
        UserRepresentation userList = client.initClient().users().search(username.trim()).get(0);
        return userList;
    }

    @Override
    public List<RoleRepresentation> getRoles() {
        List<RoleRepresentation> list = client.initClient().roles().list();
        return list;
    }

    @Override
    public List<GroupRepresentation> getGroup() {
        List<GroupRepresentation> list = client.initClient().groups().groups();
        return list;
    }

}
