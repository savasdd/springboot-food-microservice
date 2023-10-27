package com.food.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDto implements Serializable {
    private String id;
    private String groupId;
    private String name;
    private String description;
}
