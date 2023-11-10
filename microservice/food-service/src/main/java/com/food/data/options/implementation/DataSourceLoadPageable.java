package com.food.data.options.implementation;

import com.food.data.options.DataSourceLoadOptionsBase;
import com.food.data.options.SortingInfo;
import com.food.data.page.OffsetBasedPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


public class DataSourceLoadPageable {
    private final DataSourceLoadOptionsBase dataSourceLoadOptionsBase;

    public DataSourceLoadPageable(DataSourceLoadOptionsBase dataSourceLoadOptionsBase) {
        this.dataSourceLoadOptionsBase = dataSourceLoadOptionsBase;
    }

    public Pageable getPageable() {
        List<Sort.Order> orderList = new ArrayList<>();
        if (dataSourceLoadOptionsBase.getSort() != null && dataSourceLoadOptionsBase.getSort().length > 0) {
            for (SortingInfo item : dataSourceLoadOptionsBase.getSort()) {
                orderList.add(new Sort.Order((item.getDesc() ? Sort.Direction.DESC : Sort.Direction.ASC), item.getSelector()));
            }
        } else if (dataSourceLoadOptionsBase.getPrimaryKey() != null && dataSourceLoadOptionsBase.getPrimaryKey().length > 0) {
            for (String item : dataSourceLoadOptionsBase.getPrimaryKey()) {
                orderList.add(new Sort.Order(Sort.Direction.ASC, item));
            }
        }
        Sort sort = orderList.size() > 0 ? Sort.by(orderList) : Sort.unsorted();

        if (dataSourceLoadOptionsBase.getTake() > 0) {
            return new OffsetBasedPageRequest(dataSourceLoadOptionsBase.getSkip(), dataSourceLoadOptionsBase.getTake(), sort);
        } else {
            return PageRequest.of(0, Integer.MAX_VALUE, sort);
        }
    }
}
