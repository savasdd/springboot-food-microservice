package com.food.model;

import com.food.enums.EPaymentType;
import com.food.enums.EUnitType;
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
@Entity
@Table(name = "Stock")
public class Stock extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1656753245648711747L;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "availableItems")
    private Integer availableItems;

    @Column(name = "reservedItems")
    private Integer reservedItems;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private EUnitType unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EPaymentType status;

    @Column(name = "transactionDate")
    private Date transactionDate;

}
