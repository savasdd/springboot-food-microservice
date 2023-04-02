package com.food.event;

import com.food.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEvent {

    private String message;
    private Integer status;
    private AccountDto account;
}