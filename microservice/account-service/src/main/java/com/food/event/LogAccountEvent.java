package com.food.event;

import com.food.dto.LogAccount;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogAccountEvent {

    private String message;
    private Integer status;
    private LogAccount log;
}