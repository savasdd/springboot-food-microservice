package com.food.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FoodDto extends GenericDto {

    private UUID foodId;
    private String foodName;
    private String description;
    private CategoryDto category;
}
