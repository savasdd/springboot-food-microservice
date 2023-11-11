package com.food.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.enums.EOrderType;
import com.food.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ORDERS")
@Table
public class Orders extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -48260937983492874L;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "foodId", referencedColumnName = "ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Food food;

    @Column(name = "stockId")
    private Long stockId;

    @Column(name = "paymentId")
    private Long paymentId;

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
