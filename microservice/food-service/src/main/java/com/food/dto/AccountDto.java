package com.food.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String foodName;
    private Integer foodCount;
    private BigDecimal foodPrice;
}
