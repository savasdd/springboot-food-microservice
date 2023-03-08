package com.food.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ACCOUNT_ID")
    private UUID accountId;

    @Column(name = "FOOD_ID")
    private UUID foodId;

    @Column(name = "TOTAL_COUNT")
    private Double totalCount;

    @Column(name = "TOTAL_PRICE")
    private BigDecimal totalPrice;

    @Column(name = "DESCRIPTION")
    private String description;

    @Version
    @Column(name = "VERSION")
    private Long version;

}
