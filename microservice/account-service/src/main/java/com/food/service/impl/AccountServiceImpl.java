package com.food.service.impl;

import com.food.dto.AccountDto;
import com.food.event.AccountEvent;
import com.food.model.Account;
import com.food.repository.AccountRepository;
import com.food.service.AccountService;
import com.food.utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = AccountUtils.ACCOUNT, groupId = AccountUtils.GROUP_ID)
    @Override
    public void consumeStock(AccountEvent event) {
        if(event.getAccount()!=null){
            var account=repository.findByFoodId(event.getAccount().getFoodId());
            if(account.isPresent())
                update(account.get().getAccountId(), event.getAccount());
            else
                create(event.getAccount());
        }

        log.info(event.getMessage()+" {}",event.getStatus());
    }

    @Override
    @Transactional
    public List<AccountDto> getAll(){
        var list=repository.findAll();
        var dtoList=list.stream().map(val->modelMapDto(val)).collect(Collectors.toList());
        log.info("list account {} ",list.size());
        return dtoList;
    }

    @Override
    @Transactional
    public AccountDto create(AccountDto dto){
        var model=dtoMapModel(dto);
        model.setVersion(0L);
        var newModel=repository.save(model);
        log.info("create account {} ",newModel.getAccountId());
        return modelMapDto(newModel);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
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
        return AccountDto.builder().accountId(dto.getAccountId()).foodId(dto.getFoodId()).stockId(dto.getStockId()).totalCount(dto.getTotalCount()).totalPrice(dto.getTotalPrice()).description(dto.getDescription()).build();
    }

    private Account dtoMapModel(AccountDto dto){
        return Account.builder().accountId(dto.getAccountId()).foodId(dto.getFoodId()).stockId(dto.getStockId()).totalCount(dto.getTotalCount()).totalPrice(dto.getTotalPrice()).description(dto.getDescription()).build();
    }
}
