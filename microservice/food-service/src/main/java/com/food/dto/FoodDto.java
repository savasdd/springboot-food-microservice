package com.food.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {

    private UUID foodId;
    private String foodName;
    private String foodCategoryId;
    private String description;

}
