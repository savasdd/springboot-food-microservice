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
@Table(name = "STOCK")
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "STOCK_ID", updatable = false, nullable = false)
    private UUID stockId;

    @Column(name = "FOOD_ID")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID foodId;

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
