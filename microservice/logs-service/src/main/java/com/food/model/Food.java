package com.food.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Food {

    @Id
    private String foodId;
    private String foodName;
    private String foodCategoryId;
    private String description;
}
