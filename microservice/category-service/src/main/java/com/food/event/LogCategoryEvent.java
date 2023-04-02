package com.food.event;

import com.food.dto.LogCategory;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogCategoryEvent {

    private String message;
    private Integer status;
    private LogCategory log;
}