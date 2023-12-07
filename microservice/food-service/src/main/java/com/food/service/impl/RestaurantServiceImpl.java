package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Restaurant;
import com.food.repository.RestaurantRepository;
import com.food.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }


    @Override
    public Restaurant getByOne(Long id) throws GeneralException, GeneralWarning {
        var model = repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
        return model;
    }

    @Override
    public LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        loadOptions.setRequireTotalCount(true);
        var list = repository.load(loadOptions);

        log.info("list restaurant {} ", list.getTotalCount());
        return list;
    }

    @Override
    public List<Restaurant> getAll() throws GeneralException, GeneralWarning {
        var list = repository.findAll();
        log.info("list restaurant {} ", list.size());
        return list;
    }


    @Override
    @Transactional
    public Restaurant create(Restaurant dto) throws GeneralException, GeneralWarning {
        dto.setVersion(0L);
        var newModel = repository.save(dto);

        log.info("create restaurant {} ", newModel.getId());
        return newModel;
    }

    @Override
    @Transactional
    public Restaurant update(Long id, Restaurant dto) throws GeneralException, GeneralWarning {
        var restaurants = repository.findById(id);
        var newFood = restaurants.map(var -> {
            var.setName(dto.getName() != null ? dto.getName() : var.getName());
            var.setPuan(dto.getPuan() != null ? dto.getPuan() : var.getPuan());
            var.setGeom(dto.getGeom() != null ? dto.getGeom() : var.getGeom());
            return var;
        }).get();

        var newModel = repository.save(newFood);
        log.info("update restaurant {} ", id);
        return newModel;
    }

    @Override
    @Transactional
    public Restaurant delete(Long id) throws GeneralException, GeneralWarning {
        var restaurant = repository.findById(id);
        if (restaurant.isPresent()) {
            var dto = restaurant.get();
            repository.delete(restaurant.get());
            return dto;
        } else
            return null;
    }


}
