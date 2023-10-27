package com.food.service;

import com.food.exception.GeneralException;
import com.food.model.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAll() throws GeneralException;

    Department getOne(Long id) throws GeneralException;

    Department create(Department dto) throws GeneralException;

    Department update(Long id, Department dto) throws GeneralException;

    void delete(Long id) throws GeneralException;
}
