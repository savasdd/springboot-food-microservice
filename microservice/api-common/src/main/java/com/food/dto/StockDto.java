package com.food.dto;

import com.food.enums.EPaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StockDto extends GenericDto {

    private UUID stockId;

    private UUID foodId;

    private String name;

    private BigDecimal price;

    private String description;

    private EPaymentType status;

    private Integer availableItems;

    private Integer reservedItems;

    private Date transactionDate;

}
