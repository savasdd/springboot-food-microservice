package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.model.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Department")
@Table(indexes = {
        @Index(name = "IDX_department_level", columnList = "level"),
        @Index(name = "IDX_department_name", columnList = "name"),
        @Index(name = "IDX_department_parentId", columnList = "parent_id"),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"searchCode", "isDeleted"}),
        @UniqueConstraint(columnNames = {"code", "isDeleted"})
})
//@SQLDelete(sql = "UPDATE department SET is_deleted = id WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted = 0")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "parent_id")
    private Department parent;

    @NotNull(message = "{code.NotNull}")
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull(message = "{name.NotNull}")
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "searchCode")
    private String searchCode;

    @Column(name = "level")
    private Integer level;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private Set<Department> children = new HashSet<>();

    @Transient
    private boolean hasItems;

    public boolean isHasItems() {
        return children != null && children.size() > 0;
    }

}
