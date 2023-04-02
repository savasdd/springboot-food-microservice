package com.food.event;

import com.food.dto.LogStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogStockEvent {

    private String message;
    private Integer status;
    private LogStock log;
}