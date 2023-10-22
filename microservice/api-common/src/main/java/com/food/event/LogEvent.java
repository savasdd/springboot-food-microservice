package com.food.event;

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
    private List<Object> body;
}