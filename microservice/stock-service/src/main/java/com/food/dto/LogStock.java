package com.food.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class LogStock extends AbstractGenericDto{

    private String id;

    private String username;

    private String service;

    private String method;

    private String path;

    private Long status;

    private Object body;

    private Date createDate;
}
