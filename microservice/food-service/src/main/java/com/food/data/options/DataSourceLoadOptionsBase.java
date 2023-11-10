package com.food.data.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DataSourceLoadOptionsBase {
    private static Boolean stringToLowerDefault;
    private boolean requireTotalCount;
    private boolean requireGroupCount;
    private boolean isCountQuery;
    private boolean isSummaryQuery;
    private int skip;
    private int take;
    private SortingInfo[] sort;
    private GroupingInfo[] group;
    private List<Object> filter;
    private SummaryInfo[] totalSummary;
    private SummaryInfo[] groupSummary;
    private String[] select;
    private String[] preSelect;
    private Boolean remoteSelect;
    private Boolean remoteGrouping;
    private Boolean expandLinqSumType;
    private String[] primaryKey;
    private String defaultSort;
    private Boolean stringToLower;
    private Boolean paginateViaPrimaryKey;
    private Boolean sortByPrimaryKey;
    private boolean allowAsyncOverSync;
    private String searchOperation;
    private String searchValue;
    private Object searchExpr;
    private Object userData;
    private boolean distinct;

    @JsonIgnore
    private List<Object> mandatoryFilter = new ArrayList<>();
}
