package com.food.event;

import com.food.dto.StockDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockEvent {

    private String message;
    private Integer status;
    private StockDto stock;
}