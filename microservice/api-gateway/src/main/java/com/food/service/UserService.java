package com.food.service;

import com.food.exception.GeneralException;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public interface UserService {
    UserRepresentation getUser(String username) throws GeneralException;

    List<RoleRepresentation> getRoles() throws GeneralException;

    List<GroupRepresentation> getGroup() throws GeneralException;
}
