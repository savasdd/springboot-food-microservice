package com.food.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestErrorDto {

    private Integer status;
    private String message;
}
