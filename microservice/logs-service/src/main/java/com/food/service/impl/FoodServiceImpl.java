package com.food.service.impl;

import com.food.model.LogFood;
import com.food.repository.FoodRepository;
import com.food.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository repository;

    public FoodServiceImpl(FoodRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LogFood> getLogFood() {
        return repository.findAll();
    }

    @Override
    public LogFood getOneLogFood(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    @Override
    public LogFood createLogFood(LogFood dto) {
        dto.setCreateDate(new Date());
        return repository.save(dto);
    }

    @Override
    public void deleteLogFood(String id) {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}
