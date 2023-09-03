package com.food.event;

import com.food.enums.EPaymentType;
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
    private String paymentId;
    private String stockId;
    private Integer stockCount;
    private BigDecimal amount;
    private EPaymentType status;
    private String source;

    public OrderEvent(String id, String paymentId, String stockId, Integer stockCount, BigDecimal amount) {
        this.id = id;
        this.paymentId = paymentId;
        this.stockId = stockId;
        this.stockCount = stockCount;
        this.amount = amount;
    }
}
