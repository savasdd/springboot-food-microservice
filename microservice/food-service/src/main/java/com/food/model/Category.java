package com.food.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.food.enums.ECategoryType;
import com.food.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CATEGORY")
@Table
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryType")
    private ECategoryType categoryType;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Food> foodList;
}
