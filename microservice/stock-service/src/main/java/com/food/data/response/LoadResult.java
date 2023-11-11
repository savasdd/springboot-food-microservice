package com.food.data.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadResult {
    private Object items;
    private long totalCount = 0;
    private long groupCount = 0;
    private Object summary;
}
