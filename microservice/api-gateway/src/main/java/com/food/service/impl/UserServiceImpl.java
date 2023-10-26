package com.food.service.impl;

import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final KeycloakClient client;

    public UserServiceImpl(KeycloakClient client) {
        this.client = client;
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
