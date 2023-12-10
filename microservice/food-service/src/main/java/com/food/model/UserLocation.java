package com.food.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.food.model.base.BaseEntity;
import com.food.model.geometry.deserializer.GeometryDeserializer;
import com.food.model.geometry.serializer.GeometrySerializer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "UserLocation")
@Table
@Where(clause = "is_deleted = 0")
public class UserLocation extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -48260237983493874L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "userId")
    private String userId;

    @Column(name = "description")
    private String description;

    @Column(name = "address", length = 4500)
    private String address;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @Column(columnDefinition = "geometry")
    private Geometry geom;

}
