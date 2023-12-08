package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.model.UserLocation;

import java.util.List;

public interface UserLocationService {

    List<UserLocation> getAll() throws GeneralException;

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException;

    UserLocation getOne(Long id) throws GeneralException;

    UserLocation create(UserLocation dto) throws GeneralException;

    UserLocation update(Long id, UserLocation dto) throws GeneralException;

    void delete(Long id) throws GeneralException;
}
