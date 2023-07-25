package com.food.event;

import com.food.dto.LogFood;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogFoodEvent implements Serializable {

    private String message;
    private Integer status;
    private LogFood log;
}