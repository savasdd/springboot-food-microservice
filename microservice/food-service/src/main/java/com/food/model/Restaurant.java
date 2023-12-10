package com.food.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.food.model.base.BaseEntity;
import com.food.model.geometry.deserializer.GeometryDeserializer;
import com.food.model.geometry.serializer.GeometrySerializer;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

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

    @Column(name = "name")
    private String name;

    @Column(name = "puan")
    private Double puan;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @Column(name = "geom")
    private Geometry geom;

    //@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    //private Set<Food> foodRestaurant = new HashSet<>();


}
