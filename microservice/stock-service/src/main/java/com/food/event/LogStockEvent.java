package com.food.event;

import com.food.dto.LogStock;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogStockEvent {

    private String message;
    private Integer status;
    private LogStock log;
}