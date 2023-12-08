package com.food.repository;

import com.food.model.Department;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentRepository extends JpaSpecificationExecutor<Department>, JpaRepository<Department, Long>, BaseRepository<Department, Long> {
}
