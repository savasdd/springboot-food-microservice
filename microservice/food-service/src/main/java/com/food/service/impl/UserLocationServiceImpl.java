package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.model.UserLocation;
import com.food.repository.UserLocationRepository;
import com.food.service.UserLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserLocationServiceImpl implements UserLocationService {
    private final UserLocationRepository repository;

    public UserLocationServiceImpl(UserLocationRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<UserLocation> getAll() throws GeneralException {
        return repository.findAll();
    }

    @Override
    public LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException {
        var list = repository.load(loadOptions);
        log.info("get all user-location {} ", list.getTotalCount());
        return list;
    }

    @Override
    public UserLocation getOne(Long id) throws GeneralException {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Location Not Found!"));
    }

    @Override
    public UserLocation create(UserLocation dto) throws GeneralException {
        dto.setVersion(0L);
        var model = repository.save(dto);

        log.info("create user-location {}", model.getId());
        return model;
    }

    @Override
    public UserLocation update(Long id, UserLocation dto) throws GeneralException {
        var locations = repository.findById(id);
        var update = locations.map(val -> {
            val.setUserId(dto.getUserId() != null ? dto.getUserId() : val.getUserId());
            val.setDescription(dto.getDescription() != null ? dto.getDescription() : val.getDescription());
            val.setAddress(dto.getAddress() != null ? dto.getAddress() : val.getAddress());
            val.setGeom(dto.getGeom() != null ? dto.getGeom() : val.getGeom());

            return val;
        }).get();
        var model = repository.save(update);

        log.info("update user-location {}", model.getId());
        return null;
    }

    @Override
    public void delete(Long id) throws GeneralException {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}
