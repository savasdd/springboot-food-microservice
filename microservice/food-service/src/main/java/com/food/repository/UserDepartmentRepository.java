package com.food.repository;

import com.food.model.UserDepartment;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDepartmentRepository extends JpaSpecificationExecutor<UserDepartment>, JpaRepository<UserDepartment, Long>, BaseRepository<UserDepartment, Long> {
}
