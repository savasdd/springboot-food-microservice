package com.food.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Boolean enabled;
}
