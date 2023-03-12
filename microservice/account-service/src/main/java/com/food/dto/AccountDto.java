package com.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private UUID accountId;

    private UUID foodId;

    private Double totalCount;

    private BigDecimal totalPrice;

    private String description;
}
