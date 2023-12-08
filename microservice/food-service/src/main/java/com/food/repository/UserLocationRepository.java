package com.food.repository;

import com.food.model.UserLocation;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends JpaSpecificationExecutor<UserLocation>, JpaRepository<UserLocation, Long>, BaseRepository<UserLocation, Long> {
}
