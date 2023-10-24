package com.food.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.food.enums.EOrderType;
import com.food.model.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ORDERS")
@Table
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "orderId")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "foodId", referencedColumnName = "FOOD_ID")
    private Food food;

    @Column(name = "stockId")
    private UUID stockId;

    @Column(name = "paymentId")
    private UUID paymentId;

    @Column(name = "price")
    private Double price;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EOrderType status;

    @Column(name = "createDate")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "orderDate")
    private Date orderDate;

    @Transient
    private String image;


}
