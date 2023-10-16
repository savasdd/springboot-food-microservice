package com.food.spesification.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadResult<T> {
    public List<T> data;
    public long totalCount;
    private List<LoadResultGroup> groupData;
    private long groupCount;
    private boolean success;
    private Long errorId;
    public List<T> result;
}
