package com.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {

    private UUID foodId;
    private String foodName;
    private String foodCategoryId;
    private String description;

}
