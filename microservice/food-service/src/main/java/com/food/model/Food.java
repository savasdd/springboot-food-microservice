package com.food.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_FOOD")
public class Food {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "FOOD_ID")
    private UUID foodId;

    @Column(name = "FOOD_TYPE")
    private String foodName;

    @Column(name = "CATEGORY_ID")
    private String foodCategoryId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Version
    @Column(name = "VERSION")
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
