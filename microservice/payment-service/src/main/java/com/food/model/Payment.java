package com.food.model;

import com.food.enums.EPaymentType;
import com.food.model.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "PAYMENT")
public class Payment extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1656703945648711747L;

    @Column(name = "STOCK_ID")
    private Long stockId;

    @Column(name = "AMOUNT_AVAILABLE")
    private BigDecimal amountAvailable;

    @Column(name = "AMOUNT_RESERVED")
    private BigDecimal amountReserved;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private EPaymentType status;

    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;
}
