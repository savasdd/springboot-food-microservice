package com.food.event;

import com.food.dto.LogStock;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogStockEvent implements Serializable {

    private String message;
    private Integer status;
    private LogStock log;
}