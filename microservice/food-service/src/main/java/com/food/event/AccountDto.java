package com.food.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String foodName;
    private Integer foodCount;
    private BigDecimal foodPrice;
}
