package com.food.service.impl;

import com.food.dto.FoodDto;
import com.food.dto.StockDto;
import com.food.event.StockEvent;
import com.food.model.Food;
import com.food.repository.FoodRepository;
import com.food.utils.FoodUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FoodServiceImpl {

    private final KafkaTemplate<String, StockEvent> kafkaTemplateStock;
    private final FoodRepository repository;

    @KafkaListener(topics = FoodUtils.ACCOUNT, groupId = FoodUtils.GROUP_ID)
    public void consumeAccount(FoodDto dto) {
        log.info("Kafka received food {} ",dto);
    }

    public List<FoodDto> getAll(){
        var list=repository.findAll();
        var dtoList=list.stream().map(var->modelMapDto(var)).collect(Collectors.toList());
        log.info("list food {} ",list.size());
        return dtoList;
    }

    public FoodDto create(FoodDto dto){
        var model=dtoMapModel(dto);
        model.setVersion(0L);
        var newModel=repository.save(model);
        log.info("create food {} ",newModel.getFoodId());
        return modelMapDto(newModel);
    }

    public FoodDto update(UUID id,FoodDto dto){
        var foods=repository.findById(id);
        var newFood=foods.map(var->{
            var.setFoodName(dto.getFoodName());
            var.setFoodCategoryId(dto.getFoodCategoryId());
            var.setDescription(dto.getDescription());
            return var;
        });
        var newModel=repository.save(newFood.get());
        log.info("update food {} ",id);

        return modelMapDto(newModel);
    }

    public FoodDto delete(UUID id){
        var food=repository.findById(id);
        if(food.isPresent()){
            var dto=modelMapDto(food.get());
            repository.delete(food.get());
            return dto;
        }else
            return null;
    }

    public StockEvent producerStockCreate(UUID foodId,StockDto dto){
        var food=repository.findById(foodId);
        StockEvent event = new StockEvent();

        if(food.isPresent()) {
            dto.setFoodId(foodId);
            dto.setDescription(food.get().getFoodName()+" adet: "+ dto.getCount() + ", tutar: " + dto.getPrice());

            event.setMessage("food producer create stock");
            event.setStatus(200);
            event.setStock(dto);
            kafkaTemplateStock.send(FoodUtils.STOCK, event);
            log.info("food producer stock {} ",foodId);
        }
        else
            event.setMessage("food not found: "+foodId);

        return event;
    }


    private Food dtoMapModel(FoodDto dto){
       return Food.builder().foodId(dto.getFoodId()).foodName(dto.getFoodName()).foodCategoryId(dto.getFoodCategoryId()).description(dto.getDescription()).build();
    }

    private FoodDto modelMapDto(Food dto){
        return FoodDto.builder().foodId(dto.getFoodId()).foodName(dto.getFoodName()).foodCategoryId(dto.getFoodCategoryId()).description(dto.getDescription()).build();
    }

}
