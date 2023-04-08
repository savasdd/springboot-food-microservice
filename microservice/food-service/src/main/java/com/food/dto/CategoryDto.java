package com.food.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto extends AbstractGenericDto {

    private UUID categoryId;
    private String categoryName;
    private String description;
}
