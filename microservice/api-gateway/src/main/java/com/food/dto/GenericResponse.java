package com.food.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse<D> {

    private List<D> data;
    private Integer totalCount;

}
