package com.food.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Category {

    @Id
    private String categoryId;
    private String categoryName;
    private String description;
}
