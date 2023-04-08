package com.food.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto extends AbstractGenericDto {

    private UUID categoryId;

    private String categoryName;

    private String description;

}
