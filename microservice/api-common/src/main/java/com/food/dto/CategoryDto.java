package com.food.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto extends GenericDto {

    private Long id;
    private String name;
    private String description;
}
