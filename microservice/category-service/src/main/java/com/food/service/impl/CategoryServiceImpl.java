package com.food.service.impl;

import com.food.dto.CategoryDto;
import com.food.model.Category;
import com.food.repository.CategoryRepository;
import com.food.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoryDto> getAll(){
        var list=repository.findAll();
        var dtoList= list.stream().map(val->modelMapDto(val)).collect(Collectors.toList());

        log.info("category list {} ",list.size());
        return dtoList;
    }

    @Override
    public CategoryDto create(CategoryDto dto){
        var model=dtoMapModel(dto);
        model.setVersion(0L);
        var newModel=repository.save(model);
        log.info("create category {}",newModel.getCategoryId());

        return modelMapDto(newModel);
    }

    @Override
    public CategoryDto update(UUID id,CategoryDto dto){
        var categoris=repository.findById(id);
        var updateDto=categoris.map(val->{
           val.setCategoryName(dto.getCategoryName());
           val.setDescription(dto.getDescription());
            return  val;
        });
        var newModel=repository.save(updateDto.get());
        log.info("update category {} ",newModel.getCategoryId());

        return modelMapDto(newModel);
    }

    @Override
    public CategoryDto delete(UUID id){
        var model=repository.findById(id);
        if(model.isPresent()){
            var dto=modelMapDto(model.get());
            repository.delete(model.get());
            log.info("delete category {} ",id);
            return dto;
        }else
            return null;
    }


    private Category dtoMapModel(CategoryDto dto){
        return Category.builder().categoryId(dto.getCategoryId()).categoryName(dto.getCategoryName()).description(dto.getDescription()).build();
    }

    private CategoryDto modelMapDto(Category dto){
        return CategoryDto.builder().categoryId(dto.getCategoryId()).categoryName(dto.getCategoryName()).description(dto.getDescription()).build();
    }
}
