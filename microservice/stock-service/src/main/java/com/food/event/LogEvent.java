package com.food.event;

import com.food.dto.LogDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEvent {

    private String message;
    private Integer status;
    private LogDto log;
}