package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLocation extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -48260237983493874L;

    @Column(name = "userId")
    private String userId;

    @Column(name = "description")
    private String description;

    @Column(name = "geom")
    private String geom;

}
