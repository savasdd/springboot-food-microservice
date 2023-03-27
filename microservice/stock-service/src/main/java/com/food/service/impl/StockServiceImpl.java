package com.food.service.impl;

import com.food.dto.StockDto;
import com.food.event.StockEvent;
import com.food.model.Stock;
import com.food.repository.StockRepository;
import com.food.utils.StockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockServiceImpl {

    private final StockRepository repository;

    public List<StockDto> getAll(){
        var list=repository.findAll();
        var dtolList=list.stream().map(val->modelMapDto(val)).collect(Collectors.toList());
        log.info("list stock {} ",list.size());
        return dtolList;
    }

    @KafkaListener(topics = StockUtils.STOCK, groupId = StockUtils.GROUP_ID)
    public void consumeFood(StockEvent event) {
        if(event.getStock()!=null){
            if(event.getStock().getStockId()!=null)
                update(event.getStock().getStockId(),event.getStock());
            else
                create(event.getStock());
        }

        log.info(event.getMessage()+" {}",event.getStatus());
    }

    public StockDto create(StockDto dto){
        var model=dtoMapModel(dto);
        model.setVersion(0L);
        var newModel=repository.save(model);
        log.info("create stock {} ",newModel.getStockId());
        return modelMapDto(newModel);
    }

    public StockDto update(UUID id,StockDto dto){
        var stocks=repository.findById(id);
        var newStock=stocks.map(val->{
            val.setFoodId(dto.getFoodId());
            val.setCount(dto.getCount());
            val.setPrice(dto.getPrice());
            val.setDescription(dto.getDescription());
            return val;
        });
        var model=repository.save(newStock.get());
        log.info("update stock {} ",id);
        return modelMapDto(model);
    }

    public StockDto delete(UUID id){
        var model=repository.findById(id);
        if(model.isPresent()){
            var dto=modelMapDto(model.get());
            repository.delete(model.get());
            log.info("delete stock {} ",id);
            return dto;
        }else
            return null;
    }

    private StockDto modelMapDto(Stock dto){
        return StockDto.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).count(dto.getCount()).price(dto.getPrice()).description(dto.getDescription()).build();
    }

    private Stock dtoMapModel(StockDto dto){
        return Stock.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).count(dto.getCount()).price(dto.getPrice()).description(dto.getDescription()).build();
    }
}
