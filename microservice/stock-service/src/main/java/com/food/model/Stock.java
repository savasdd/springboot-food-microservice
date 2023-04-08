package com.food.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "TBL_STOCK")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "STOCK_ID", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID stockId;

    @Column(name = "FOOD_ID", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID foodId;

    @Column(name = "COUNT")
    private Double count;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Version
    private Long version;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private String updatedBy;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;
}
