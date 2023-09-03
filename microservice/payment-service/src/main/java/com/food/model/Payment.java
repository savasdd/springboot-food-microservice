package com.food.model;

import com.food.enums.EPaymentType;
import com.food.model.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "PAYMENT")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "PAYMENT_ID", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID paymentId;

    @Column(name = "STOCK_ID", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID stockId;

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
