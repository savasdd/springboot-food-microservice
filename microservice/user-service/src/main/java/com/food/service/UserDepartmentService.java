package com.food.service;

import com.food.exception.GeneralException;
import com.food.model.Department;
import com.food.model.UserDepartment;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;

public interface UserDepartmentService {

    List<UserDepartment> getAll() throws GeneralException;
    LoadResult<UserDepartment> getAll(DataSourceLoadOptions<UserDepartment> loadOptions) throws GeneralException;

    UserDepartment getOne(Long id) throws GeneralException;

    UserDepartment create(UserDepartment dto) throws GeneralException;

    UserDepartment update(Long id, UserDepartment dto) throws GeneralException;

    void delete(Long id) throws GeneralException;
}
