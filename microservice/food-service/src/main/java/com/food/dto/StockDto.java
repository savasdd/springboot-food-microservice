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
public class StockDto extends AbstractGenericDto {

    private UUID stockId;

    private UUID foodId;

    private Double count;

    private BigDecimal price;

    private String description;

}
