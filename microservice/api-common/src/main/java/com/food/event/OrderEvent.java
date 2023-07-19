package com.food.event;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEvent implements Serializable {

    private String id;
    private String customerId;
    private String stockId;
    private Integer stockCount;
    private BigDecimal price;
    private String status;
    private String source;

    public OrderEvent(String id, String customerId, String stockId, Integer stockCount, BigDecimal price) {
        this.id = id;
        this.customerId = customerId;
        this.stockId = stockId;
        this.stockCount = stockCount;
        this.price = price;
    }
}
