package com.food.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountDto extends AbstractGenericDto {

    private UUID accountId;

    private UUID foodId;

    private UUID stockId;

    private Double totalCount;

    private BigDecimal totalPrice;

    private String description;
}
