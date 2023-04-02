package com.food.event;

import com.food.dto.LogDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEvent {

    private String message;
    private Integer status;
    private LogDto log;
}