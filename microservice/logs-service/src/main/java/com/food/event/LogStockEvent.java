package com.food.event;

import com.food.model.LogStock;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogStockEvent {

    private String message;
    private Integer status;
    private LogStock log;
}