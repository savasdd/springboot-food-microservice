package com.food.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto extends AbstractGenericDto {
    private String username;
    private String password;
}
