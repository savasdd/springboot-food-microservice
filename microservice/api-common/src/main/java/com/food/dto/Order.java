package com.food.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Order implements Serializable {

    private Long id;
    private Long customerId;
    private String stockId;
    private Integer stockCount;
    private Integer price;
    private String status;
    private String source;

    public Order() {
    }

    public Order(Long id, Long customerId, String stockId, String status) {
        this.id = id;
        this.customerId = customerId;
        this.stockId = stockId;
        this.status = status;
    }

    public Order(Long id, Long customerId, String stockId, Integer stockCount, Integer price) {
        this.id = id;
        this.customerId = customerId;
        this.stockId = stockId;
        this.stockCount = stockCount;
        this.price = price;
        this.status = "NEW";
    }
}
