package com.food.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto extends GenericDto {

    private UUID categoryId;
    private String categoryName;
    private String description;
}
