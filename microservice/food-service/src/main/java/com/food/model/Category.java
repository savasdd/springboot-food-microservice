package com.food.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.food.enums.ECategoryType;
import com.food.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CATEGORY")
@Table
public class Category extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -28260237983492874L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryType")
    private ECategoryType categoryType;

//    @OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
//    @JsonManagedReference("category")
//    private List<Food> foodList;
}
