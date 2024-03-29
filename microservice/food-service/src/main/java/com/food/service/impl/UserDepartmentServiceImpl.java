package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.model.UserDepartment;
import com.food.repository.UserDepartmentRepository;
import com.food.service.DepartmentService;
import com.food.service.UserDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserDepartmentServiceImpl implements UserDepartmentService {
    private final UserDepartmentRepository repository;
    private final DepartmentService service;

    public UserDepartmentServiceImpl(UserDepartmentRepository repository, DepartmentService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public List<UserDepartment> getAll() throws GeneralException {
        return repository.findAll();
    }

    @Override
    public LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException {
        var list = repository.load(loadOptions);
        log.info("get all user-department {} ", list.getTotalCount());
        return list;
    }

    @Override
    public UserDepartment getOne(Long id) throws GeneralException {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found!"));
    }

    @Override
    public UserDepartment create(UserDepartment dto) throws GeneralException {
        dto.setDepartment(dto.getDepartment().getId() != null ? service.getOne(dto.getDepartment().getId()) : null);
        var model = repository.save(dto);

        log.info("create user {}", model.getId());
        return model;
    }

    @Override
    public UserDepartment update(Long id, UserDepartment dto) throws GeneralException {
        var users = repository.findById(id);
        var update = users.map(val -> {
            try {
                val.setUserId(dto.getUserId() != null ? dto.getUserId() : val.getUserId());
                val.setDepartment(dto.getDepartment().getId() != null ? service.getOne(dto.getDepartment().getId()) : val.getDepartment());
                val.setDescription(dto.getDescription() != null ? dto.getDescription() : val.getDescription());
            } catch (GeneralException e) {
            }
            return val;
        }).get();
        var model = repository.save(update);

        log.info("update user {}", model.getId());
        return null;
    }

    @Override
    public void delete(Long id) throws GeneralException {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}
