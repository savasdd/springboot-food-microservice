package com.food.event;

import com.food.enums.ELogType;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEvent implements Serializable {

    private String username;
    private String service;
    private String message;
    private Integer status;
    private ELogType logType;
    private List<Object> body;
}