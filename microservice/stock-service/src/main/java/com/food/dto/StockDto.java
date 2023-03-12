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
public class StockDto {

    private UUID stockId;

    private UUID foodId;

    private Double count;

    private BigDecimal price;

    private String description;

}
