package com.food.model;

import com.food.model.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "FOOD")
@Table
@Check(constraints = "LENGTH(DESCRIPTION) > 2")
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "FOOD_ID")
    private UUID foodId;

    @Column(name = "FOOD_NAME")
    private String foodName;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private Category category;

    @Length(min = 2)
    @Column(name = "DESCRIPTION")
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
