package com.food.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.enums.EUnitType;
import com.food.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StockProduct")
public class StockProduct extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1656753249648711747L;

    @Column(name = "foodId")
    private Long foodId;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Stock stock;

}
