package com.food.service.impl;

import com.food.enums.ELogType;
import com.food.exception.GeneralException;
import com.food.model.Department;
import com.food.repository.DepartmentRepository;
import com.food.service.DepartmentService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Department> getAll() throws GeneralException {
        var list = repository.findAll();

        log.info("get all {}", list.size());
        return list;
    }

    @Override
    public LoadResult<Department> getAll(DataSourceLoadOptions<Department> loadOptions) throws GeneralException {
        LoadResult<Department> loadResult = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());

        loadResult.setData(list.getContent());
        loadResult.setTotalCount(list.stream().count());
        //logService.eventLog("api/foods", List.of(loadResult), 200, ELogType.FOOD);
        log.info("get all {} ", loadResult.totalCount);
        return loadResult;
    }

    @Override
    public Department getOne(Long id) throws GeneralException {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    @Override
    public Department create(Department dto) throws GeneralException {
        if (dto.getParent() == null || dto.getParent().getId() == null)
            dto.setParent(null);
        else
            dto.setParent(getOne(dto.getParent().getId()));


        dto.setSearchCode(dto.getSearchCode() != null ? dto.getSearchCode() : dto.getCode());
        var model = repository.save(dto);
        log.info("creat {}", model.getId());
        return model;
    }

    @Override
    public Department update(Long id, Department dto) throws GeneralException {
        var department = repository.findById(id);
        var update = department.map(val -> {
            val.setParent(dto.getParent() != null ? dto.getParent() : val.getParent());
            val.setCode(dto.getCode() != null ? dto.getCode() : val.getCode());
            val.setName(dto.getName() != null ? dto.getName() : val.getName());
            val.setSearchCode(dto.getSearchCode() != null ? dto.getSearchCode() : val.getSearchCode());
            val.setLevel(dto.getLevel() != null ? dto.getLevel() : val.getLevel());
            return val;
        }).get();

        var model = repository.save(update);
        log.info("creat {}", model.getId());
        return model;
    }

    @Override
    public void delete(Long id) throws GeneralException {
        if (repository.existsById(id))
            repository.deleteById(id);

    }
}
