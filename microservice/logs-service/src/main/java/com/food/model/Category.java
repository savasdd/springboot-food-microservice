package com.food.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document
public class Category {

    @Id
    private String id;

    private String username;

    private String service;

    private String method;

    private String path;

    private Long status;

    private Object body;

    private Date createDate;
}
