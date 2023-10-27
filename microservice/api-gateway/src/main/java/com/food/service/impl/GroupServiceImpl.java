package com.food.service.impl;

import com.food.dto.GenericResponse;
import com.food.dto.GroupDto;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

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
    public GenericResponse getGroupRol(String id) throws GeneralException {
        var response = new GenericResponse<RoleRepresentation>();
        GroupResource groupResource = resource.groups().group(id);
        var list = groupResource.roles().realmLevel().listAll();
        response.setData(list);
        response.setTotalCount(list.size());
        return response;
    }

    @Override
    public GroupDto createGroup(GroupDto dto) throws GeneralException {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(dto.getName());
        resource.groups().add(group);
        return dto;
    }

    @Override
    public RolDto addGroupRol(RolDto dto) throws GeneralException {
        GroupResource groupResource = resource.groups().group(dto.getGroupId());
        RoleRepresentation role = new RoleRepresentation();
        role.setName(dto.getName());
        role.setId(dto.getId());
        groupResource.roles().realmLevel().add(List.of(role));
        return dto;
    }

    @Override
    public RolDto leaveGroupRol(RolDto dto) throws GeneralException {
        GroupResource groupResource = resource.groups().group(dto.getGroupId());
        RoleRepresentation role = new RoleRepresentation();
        role.setName(dto.getName());
        role.setId(dto.getId());
        groupResource.roles().realmLevel().remove(List.of(role));
        return dto;
    }


}
