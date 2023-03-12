package com.food.service.impl;

import com.food.dto.AccountDto;
import com.food.dto.FoodDto;
import com.food.model.Account;
import com.food.repository.AccountRepository;
import com.food.utils.AccountUtils;
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
public class AccountServiceImpl {

    private final KafkaTemplate<String, FoodDto> kafkaTemplate;
    private final AccountRepository repository;

    @KafkaListener(topics = AccountUtils.FOOD, groupId = AccountUtils.GROUP_ID)
    public void consumeFood(AccountDto dto) {
        log.info("Kafka received account {} ",dto);
    }

    public void sendFood(){
        FoodDto dto=new FoodDto();
        dto.setFoodName("Şeftali");
        dto.setFoodType("Meyve");
        dto.setDescription("Tüylü şeftali!");

        kafkaTemplate.send(AccountUtils.ACCOUNT,dto);
    }

    public List<AccountDto> getAll(){
        var list=repository.findAll();
        var dtoList=list.stream().map(val->modelMapDto(val)).collect(Collectors.toList());
        log.info("list account {} ",list.size());
        return dtoList;
    }

    public AccountDto create(AccountDto dto){
        var model=dtoMapModel(dto);
        model.setVersion(0L);
        var newModel=repository.save(model);
        log.info("create account {} ",newModel.getAccountId());
        return modelMapDto(newModel);
    }

    public AccountDto update(UUID id,AccountDto dto){
        var accounts=repository.findById(id);
        var newAccount=accounts.map(val->{
           val.setFoodId(dto.getFoodId());
           val.setTotalCount(dto.getTotalCount());
            val.setTotalPrice(dto.getTotalPrice());
            val.setDescription(dto.getDescription());
            return val;
        });
        var model=repository.save(newAccount.get());
        log.info("update account {} ",id);
        return modelMapDto(model);
    }

    public AccountDto delete(UUID id){
        var model=repository.findById(id);
        if(model.isPresent()){
            var dto=modelMapDto(model.get());
            repository.delete(model.get());
            log.info("delete account {} ",id);
            return dto;
        }
        else
            return null;
    }


    private AccountDto modelMapDto(Account dto){
        return AccountDto.builder().accountId(dto.getAccountId()).foodId(dto.getFoodId()).totalCount(dto.getTotalCount()).totalPrice(dto.getTotalPrice()).description(dto.getDescription()).build();
    }

    private Account dtoMapModel(AccountDto dto){
        return Account.builder().accountId(dto.getAccountId()).foodId(dto.getFoodId()).totalCount(dto.getTotalCount()).totalPrice(dto.getTotalPrice()).description(dto.getDescription()).build();
    }
}
