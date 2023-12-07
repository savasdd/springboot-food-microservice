package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.enums.EClassType;
import com.food.enums.EPaymentType;
import com.food.model.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "FOOD")
@Table
@Check(constraints = "LENGTH(FOOD_NAME) > 1")
public class Food extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -48260237983492874L;

    @Column(name = "FOOD_NAME")
    private String foodName;

    @Column(name = "PRICE")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@JsonBackReference("category")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Restaurant restaurant;

    @Length(min = 2)
    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "classType")
    private EClassType classType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private EPaymentType status;

    @Transient
    private String image;

}
