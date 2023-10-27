package com.food.service.impl;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.RolService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RolServiceImpl implements RolService {

    private final KeycloakClient client;
    private final RealmResource resource;

    public RolServiceImpl(KeycloakClient client) {
        this.client = client;
        this.resource = client.initClient();
    }

    @Override
    public GenericResponse getRoles() throws GeneralException {
        var response = new GenericResponse<RoleRepresentation>();
        List<RoleRepresentation> list = resource.roles().list();
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
        resource.roles().create(rol);
        return dto;
    }

}
