package com.food.data.options.implementation;

import com.food.data.options.DataSourceLoadOptionsBase;
import com.food.data.options.SortingInfo;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


public class DataSourceLoadSortable {
    private final DataSourceLoadOptionsBase dataSourceLoadOptionsBase;

    public DataSourceLoadSortable(DataSourceLoadOptionsBase dataSourceLoadOptionsBase) {
        this.dataSourceLoadOptionsBase = dataSourceLoadOptionsBase;
    }

    public Sort getSortable() {
        if (dataSourceLoadOptionsBase.getSort() == null) return null;
        List<Sort.Order> orderList = new ArrayList<>();
        for (SortingInfo item : dataSourceLoadOptionsBase.getSort()) {
            orderList.add(new Sort.Order((item.getDesc() ? Sort.Direction.DESC : Sort.Direction.ASC), item.getSelector()));
        }
        return Sort.by(orderList);
    }
}
