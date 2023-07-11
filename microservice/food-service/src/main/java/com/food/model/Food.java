package com.food.model;

import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "TBL_FOOD")
//@Table(name = "TBL_FOOD")
@Check(constraints = "LENGTH(DESCRIPTION) > 2")
public class Food implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "FOOD_ID")
    private UUID foodId;

    @Column(name = "FOOD_NAME")
    private String foodName;

    @Column(name = "CATEGORY_ID")
    private String foodCategoryId;

    @Length(min = 11, max = 11)
    @Column(name = "DESCRIPTION", length = 11)
    private String description;

    @Version
    @Column(name = "VERSYION")
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
