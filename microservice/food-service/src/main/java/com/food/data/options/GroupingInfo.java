package com.food.data.options;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GroupingInfo extends SortingInfo {
    private String groupInterval;
    private Boolean isExpanded;

    public Boolean getIsExpanded() {
        if (isExpanded == null) {
            return true;
        }
        return isExpanded;
    }
}
