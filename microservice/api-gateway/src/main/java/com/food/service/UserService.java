package com.food.service;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public interface UserService {
    UserRepresentation getUser(String username) throws GeneralException;

    GenericResponse getAllUser() throws GeneralException;

    GenericResponse getRoles() throws GeneralException;

    RolDto createRoles(RolDto dto) throws GeneralException;

    GenericResponse getGroup() throws GeneralException;
}
