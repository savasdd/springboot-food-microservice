package com.food.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRolDto extends AbstractGenericDto {
    private String rol;
}
