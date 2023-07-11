package com.food.event;

import com.food.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEvent {

    private String message;
    private Integer status;
    private CategoryDto category;
}
