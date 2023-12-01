package com.food.service.impl;

import com.food.model.LogUser;
import com.food.repository.UserRepository;
import com.food.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LogUser> getLog() {
        return repository.findAll();
    }

    @Override
    public LogUser getOneLog(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    @Override
    public LogUser createLog(LogUser dto) {
        dto.setCreateDate(new Date());
        return repository.save(dto);
    }

    @Override
    public void deleteLog(String id) {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}
