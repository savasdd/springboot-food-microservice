package com.food.model;

import com.food.enums.ELogType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document
public class LogPayment implements Serializable {

    @Id
    private String id;

    private String username;

    private String service;

    private String method;

    private String path;

    private Integer status;

    private List<Object> body;

    private ELogType logType;

    private Date createDate;
}
