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
public class StockDto extends GenericDto {

    private UUID stockId;

    private UUID foodId;

    private BigDecimal price;

    private String description;

    private String status;

    private Integer availableItems;

    private Integer reservedItems;

}
