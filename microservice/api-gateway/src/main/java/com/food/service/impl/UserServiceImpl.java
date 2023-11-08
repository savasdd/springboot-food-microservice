package com.food.service.impl;

import com.food.dto.GenericResponse;
import com.food.dto.GroupDto;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private KeycloakClient client;


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
        return response;
    }

    @Override
    public GenericResponse getUserGroup(String userId) throws GeneralException {
        var response = new GenericResponse<GroupRepresentation>();
        UserResource userResource = client.initClient().users().get(userId);
        var list = userResource.groups();
        response.setData(list);
        response.setTotalCount(list.size());

        return response;
    }

    @Override
    public GroupDto joinUserGroup(GroupDto dto) throws GeneralException {
        UserResource userResource = client.initClient().users().get(dto.getUserId());
        userResource.joinGroup(dto.getId());
        return dto;
    }

    @Override
    public GroupDto leaveUserGroup(GroupDto dto) throws GeneralException {
        UserResource userResource = client.initClient().users().get(dto.getUserId());
        userResource.leaveGroup(dto.getId());
        return dto;
    }

    @Override
    public UserDto createUser(UserDto dto) throws GeneralException {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(dto.getEnabled());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAttributes(Collections.singletonMap("origin", Arrays.asList("Food Users")));

        Response response = client.initClient().users().create(user);
        log.info("Repsonse: {} {}", response.getStatus());
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = client.initClient().users().get(userId);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(dto.getPassword());
        userResource.resetPassword(passwordCred);
        log.info("create user {}", userId);
        return dto;
    }

    @Override
    public UserDto deleteUser(String id) throws GeneralException {
        UserResource userResource = client.initClient().users().get(id);
        userResource.remove();
        return UserDto.builder().id(id).build();
    }


}
