package com.food.service;

import com.food.dto.AccountDto;
import com.food.event.AccountEvent;
import com.food.service.impl.AccountServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    public void consumeStock(AccountEvent event);
    public List<AccountDto> getAll();
    public AccountDto create(AccountDto dto);
    public AccountDto update(UUID id, AccountDto dto);

    public AccountDto delete(UUID id);
}
