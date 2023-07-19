package com.food.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order implements Serializable {

    private String id;
    private String customerId;
    private String stockId;
    private Integer stockCount;
    private Integer price;
    private String status;
    private String source;

    public Order(String id, String customerId, String stockId, Integer stockCount, Integer price) {
        this.id = id;
        this.customerId = customerId;
        this.stockId = stockId;
        this.stockCount = stockCount;
        this.price = price;
        this.status = "NEW";
    }
}
