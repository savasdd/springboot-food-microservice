package com.food.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1656703245648711747L;

    @JsonIgnore
    @Column(name = "createdDate", updatable = false)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "updatedDate")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "isDeleted", nullable = false)
    private Long isDeleted;

    @PreUpdate
    public void setPreUpdate() {
        this.updatedDate = new Date();
    }

    @PrePersist
    public void setPrePersist() {
        this.createdDate = new Date();
        this.isDeleted = 0L;
    }

}
