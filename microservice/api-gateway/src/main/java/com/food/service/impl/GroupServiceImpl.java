package com.food.service.impl;

import com.food.dto.GenericResponse;
import com.food.dto.GroupDto;
import com.food.dto.RolDto;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.GroupService;
import com.food.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private final KeycloakClient client;
    private final RealmResource resource;

    public GroupServiceImpl(KeycloakClient client) {
        this.client = client;
        this.resource = client.initClient();
    }

    @Override
    public GenericResponse getGroup() throws GeneralException {
        var response = new GenericResponse<GroupRepresentation>();
        List<GroupRepresentation> list = resource.groups().groups();
        response.setData(list);
        response.setTotalCount(list.size());

        log.info("get group {}", list.size());
        return response;
    }

    @Override
    public GroupDto createGroup(GroupDto dto) throws GeneralException {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(dto.getName());
        resource.groups().add(group);
        return dto;
    }




}
