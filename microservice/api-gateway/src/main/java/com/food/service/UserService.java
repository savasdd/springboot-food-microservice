package com.food.service;

import com.food.dto.GenericResponse;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import org.keycloak.representations.idm.UserRepresentation;


public interface UserService {
    UserRepresentation getUser(String username) throws GeneralException;

    GenericResponse getAllUser() throws GeneralException;

    GenericResponse getUserGroup(String userId) throws GeneralException;

    UserDto createUser(UserDto dto) throws GeneralException;

    UserDto deleteUser(String id) throws GeneralException;

}
