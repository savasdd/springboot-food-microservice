package com.food.service;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;


public interface RolService {

    GenericResponse getRoles() throws GeneralException;

    RolDto createRoles(RolDto dto) throws GeneralException;

}
