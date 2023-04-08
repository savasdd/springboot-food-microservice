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
public class FoodDto extends AbstractGenericDto{

    private UUID foodId;
    private String foodName;
    private String foodCategoryId;
    private String description;
}
