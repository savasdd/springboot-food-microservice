package com.food.service.impl;

import com.food.dto.AccountDto;
import com.food.dto.StockDto;
import com.food.event.AccountEvent;
import com.food.model.Stock;
import com.food.repository.StockRepository;
import com.food.service.StockService;
import com.food.utils.StockUtils;
import com.food.utils.aop.MongoLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockServiceImpl implements StockService {
    private final KafkaTemplate<String, AccountEvent> kafkaTemplateAccount;
    private final StockRepository repository;

    public StockServiceImpl(KafkaTemplate<String, AccountEvent> kafkaTemplateAccount, StockRepository repository) {
        this.kafkaTemplateAccount = kafkaTemplateAccount;
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<StockDto> getAll(UUID foodId){
        var list=repository.findByFoodId(foodId);
        var dtolList=list.stream().map(val->modelMapDto(val)).collect(Collectors.toList());
        log.info("list stock {} ",list.size());
        return dtolList;
    }

    @MongoLog(status = 201)
    @Override
    @Transactional
    public StockDto create(UUID foodId,StockDto dto){
        var model=dtoMapModel(dto);
        model.setVersion(0L);
        model.setFoodId(foodId);
        var newModel=repository.save(model);
        log.info("create stock {} ",newModel.getStockId());
        producerAccount(newModel);
        return modelMapDto(newModel);
    }

    @MongoLog(status = 200)
    @Override
    @Transactional
    public StockDto update(UUID foodId,UUID id,StockDto dto){
        var stocks=repository.findByFoodIdAndStockId(foodId,id);
        var newStock=stocks.map(val->{
            val.setFoodId(dto.getFoodId());
            val.setCount(dto.getCount());
            val.setPrice(dto.getPrice());
            val.setDescription(dto.getDescription());
            return val;
        });
        var model=repository.save(newStock.get());
        producerAccount(model);
        log.info("update stock {} ",id);
        return modelMapDto(model);
    }

    @MongoLog(status = 202)
    @Override
    @Transactional
    public StockDto delete(UUID foodId,UUID id){
        var model=repository.findByFoodIdAndStockId(foodId,id);
        if(model.isPresent()){
            var dto=modelMapDto(model.get());
            producerAccount(model.get());
            repository.delete(model.get());
            log.info("delete stock {} ",id);
            return dto;
        }else
            return null;
    }


    private void producerAccount(Stock dto){
        AccountEvent event = new AccountEvent();
        var account= new AccountDto();
        var stocks=repository.findByFoodId(dto.getFoodId());

        if(!stocks.isEmpty()){
            Double totalCount=stocks.stream().map(f->f.getCount()).reduce(0.0,Double::sum);
            BigDecimal totalPrice=stocks.stream().map(f->f.getPrice()).reduce(BigDecimal.ZERO,BigDecimal::add);

            account.setFoodId(dto.getFoodId());
            account.setStockId(dto.getStockId());
            account.setTotalCount(totalCount);
            account.setTotalPrice(totalPrice);
            account.setDescription("total count: "+totalCount+", total price: "+totalPrice);

            event.setMessage("stock producer create account");
            event.setStatus(200);
            event.setAccount(account);
            kafkaTemplateAccount.send(StockUtils.ACCOUNT, event);
            log.info("stock producer account {} ",dto.getStockId());
        }else
            event.setMessage("food not found: "+dto.getFoodId());

    }

    private StockDto modelMapDto(Stock dto){
        return StockDto.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).count(dto.getCount()).price(dto.getPrice()).description(dto.getDescription()).build();
    }

    private Stock dtoMapModel(StockDto dto){
        return Stock.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).count(dto.getCount()).price(dto.getPrice()).description(dto.getDescription()).build();
    }
}
