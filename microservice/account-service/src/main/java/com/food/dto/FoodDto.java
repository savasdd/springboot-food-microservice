package com.food.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FoodDto extends AbstractGenericDto {
    private String foodName;
    private String foodType;
    private String description;

}
