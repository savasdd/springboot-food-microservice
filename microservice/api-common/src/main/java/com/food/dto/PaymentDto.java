package com.food.dto;

import com.food.enums.EPaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentDto extends GenericDto {

    private UUID paymentId;

    private UUID stockId;

    private BigDecimal amountAvailable;

    private BigDecimal amountReserved;

    private BigDecimal amount;

    private EPaymentType status;

    private Date transactionDate;

}
