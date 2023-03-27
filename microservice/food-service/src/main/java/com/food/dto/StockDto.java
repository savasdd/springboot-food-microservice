package com.food.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {

    private UUID stockId;

    private UUID foodId;

    private Double count;

    private BigDecimal price;

    private String description;

}
