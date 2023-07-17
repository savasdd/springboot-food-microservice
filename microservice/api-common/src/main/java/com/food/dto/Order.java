package com.food.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Order implements Serializable {

    private Long id;
    private Long customerId;
    private String foodId;
    private int foodCount;
    private int price;
    private String status;
    private String source;

    public Order() {
    }

    public Order(Long id, Long customerId, String foodId, String status) {
        this.id = id;
        this.customerId = customerId;
        this.foodId = foodId;
        this.status = status;
    }

    public Order(Long id, Long customerId, String foodId, int foodCount, int price) {
        this.id = id;
        this.customerId = customerId;
        this.foodId = foodId;
        this.foodCount = foodCount;
        this.price = price;
        this.status = "NEW";
    }
}
