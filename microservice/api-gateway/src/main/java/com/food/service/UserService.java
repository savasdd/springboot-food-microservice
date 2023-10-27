package com.food.service;

import com.food.dto.GenericResponse;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import org.keycloak.representations.idm.UserRepresentation;


public interface UserService {
    UserRepresentation getUser(String username) throws GeneralException;

    GenericResponse getAllUser() throws GeneralException;

    UserDto createUser(UserDto dto) throws GeneralException;

    String deleteUser(String id) throws GeneralException;

}
