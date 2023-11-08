package com.food.service.impl;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import com.food.keycloak.KeycloakClient;
import com.food.service.RolService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private KeycloakClient client;

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

}
