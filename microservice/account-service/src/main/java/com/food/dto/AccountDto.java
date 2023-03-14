package com.food.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
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
