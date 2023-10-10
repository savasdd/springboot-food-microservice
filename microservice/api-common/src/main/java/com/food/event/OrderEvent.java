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
