package com.food.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.food.enums.EPaymentType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEvent {

    private String id;
    private String foodId;
    private String stockId;
    private String paymentId;
    private Integer stockCount;
    private BigDecimal amount;
    private EPaymentType status;
    private String foodName;
    private String source;
    private String message;


    public OrderEvent(String id, String paymentId, String stockId, String foodId, Integer stockCount, BigDecimal amount) {
        this.id = id;
        this.paymentId = paymentId;
        this.stockId = stockId;
        this.foodId = foodId;
        this.stockCount = stockCount;
        this.amount = amount;
    }


}
