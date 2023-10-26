package com.food.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDto implements Serializable {
    private String name;
    private String description;
}
