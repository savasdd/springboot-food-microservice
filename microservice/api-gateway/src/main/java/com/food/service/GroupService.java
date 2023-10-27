package com.food.service;

import com.food.dto.GenericResponse;
import com.food.dto.GroupDto;
import com.food.dto.RolDto;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import org.keycloak.representations.idm.UserRepresentation;


public interface GroupService {
    GroupDto createGroup(GroupDto dto) throws GeneralException;

    GenericResponse getGroup() throws GeneralException;

}
