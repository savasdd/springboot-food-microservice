package com.food.model;

import com.food.model.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "FOOD")
@Table
@Check(constraints = "LENGTH(FOOD_NAME) > 2")
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "FOOD_ID")
    private UUID foodId;

    @Column(name = "FOOD_NAME")
    private String foodName;

    @Column(name = "PRICE")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private Category category;

    @Length(min = 2)
    @Column(name = "DESCRIPTION")
    private String description;


}
