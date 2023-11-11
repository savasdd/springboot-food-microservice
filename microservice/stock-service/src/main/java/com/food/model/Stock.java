package com.food.model;

import com.food.enums.EPaymentType;
import com.food.model.base.BaseEntity;
import lombok.*;

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
@Table(name = "STOCK")
public class Stock extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1656753245648711747L;

    @Column(name = "FOOD_ID")
    private Long foodId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "AVAILABLE_ITEMS")
    private Integer availableItems;

    @Column(name = "RESERVED_ITEMS")
    private Integer reservedItems;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private EPaymentType status;

    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

}
