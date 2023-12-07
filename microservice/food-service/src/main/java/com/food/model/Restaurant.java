package com.food.model;

import com.food.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "RESTAURANT")
@Table
public class Restaurant extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -48260237983492874L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PUAN")
    private Double puan;

    @Column(name = "GEOM")
    private String geom;

    //@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    //private Set<Food> foodRestaurant = new HashSet<>();


}
