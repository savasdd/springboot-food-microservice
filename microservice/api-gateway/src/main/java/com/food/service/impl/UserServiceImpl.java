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
        log.info("get user {}", username);
        return userList;
    }

    @Override
    public List<UserRepresentation> getAllUser() throws GeneralException {
        var list = client.initClient().users().list();

        log.info("get users {}", list.size());
        return list;
    }

    @Override
    public List<RoleRepresentation> getRoles() throws GeneralException {
        List<RoleRepresentation> list = client.initClient().roles().list();

        log.info("get roles {}", list.size());
        return list;
    }

    @Override
    public List<GroupRepresentation> getGroup() throws GeneralException {
        List<GroupRepresentation> list = client.initClient().groups().groups();

        log.info("get group {}", list.size());
        return list;
    }
}
