package com.food.spesification.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadResultGroup {
    public LoadResultGroup(String key, Long count) {
        this.setKey(key);
        this.setCount(count);
        Long[] summaryValue = {this.getCount()};
        this.setSummary(summaryValue);
    }

    private String key;
    private Long count;
    private Long items;
    private Long[] summary;
}
