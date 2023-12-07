package com.food.repository;

import com.food.model.Restaurant;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaSpecificationExecutor<Restaurant>, JpaRepository<Restaurant, Long>, BaseRepository<Restaurant, Long> {
}
