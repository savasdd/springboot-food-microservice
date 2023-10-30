package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "UserDepartment")
@Table
@Where(clause = "is_deleted = 0")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDepartment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Department department;

    @Column(name = "userId")
    private String userId;

    @Column(name = "description")
    private String description;

}
