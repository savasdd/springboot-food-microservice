package com.food.event;

import com.food.dto.LogFood;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogFoodEvent {

    private String message;
    private Integer status;
    private LogFood log;
}