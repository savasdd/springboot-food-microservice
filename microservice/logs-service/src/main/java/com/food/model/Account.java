package com.food.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@Document
public class Account {

    @Id
    private String accountId;

    private String foodId;

    private Double totalCount;

    private BigDecimal totalPrice;

    private String description;
}
