package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.model.UserDepartment;

import java.util.List;

public interface UserDepartmentService {

    List<UserDepartment> getAll() throws GeneralException;

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException;

    UserDepartment getOne(Long id) throws GeneralException;

    UserDepartment create(UserDepartment dto) throws GeneralException;

    UserDepartment update(Long id, UserDepartment dto) throws GeneralException;

    void delete(Long id) throws GeneralException;
}
