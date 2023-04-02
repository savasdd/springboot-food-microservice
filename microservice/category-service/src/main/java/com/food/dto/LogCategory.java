package com.food.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogCategory {

    private String id;

    private String username;

    private String service;

    private String method;

    private String path;

    private Long status;

    private Object body;

    private Date createDate;
}
