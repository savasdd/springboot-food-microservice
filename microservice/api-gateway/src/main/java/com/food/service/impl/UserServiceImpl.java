package com.food.service.impl;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
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
    public GenericResponse getAllUser() throws GeneralException {
        var response = new GenericResponse<UserRepresentation>();
        var list = client.initClient().users().list();
        response.setData(list);
        response.setTotalCount(list.size());

        log.info("get users {}", list.size());
        return response;
    }

    @Override
    public GenericResponse getRoles() throws GeneralException {
        var response = new GenericResponse<RoleRepresentation>();
        List<RoleRepresentation> list = client.initClient().roles().list();
        response.setData(list);
        response.setTotalCount(list.size());

        log.info("get roles {}", list.size());
        return response;
    }

    @Override
    public RolDto createRoles(RolDto dto) throws GeneralException {
        RoleRepresentation rol = new RoleRepresentation();
        rol.setName(dto.getName());
        rol.setDescription(dto.getDescription());
        client.initClient().roles().create(rol);
        return dto;
    }

    @Override
    public GenericResponse getGroup() throws GeneralException {
        var response = new GenericResponse<GroupRepresentation>();
        List<GroupRepresentation> list = client.initClient().groups().groups();
        response.setData(list);
        response.setTotalCount(list.size());

        log.info("get group {}", list.size());
        return response;
    }
}
