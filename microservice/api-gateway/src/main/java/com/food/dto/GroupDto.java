package com.food.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto implements Serializable {
    private String id;
    private String userId;
    private String name;
}
