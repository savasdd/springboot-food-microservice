package com.food.data.options.implementation;

import com.food.data.expression.FilterExpressionCompiler;
import com.food.data.options.DataSourceLoadOptionsBase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


public class DataSourceLoadSpecification<T> {
    private final DataSourceLoadOptionsBase dataSourceLoadOptionsBase;

    private List<Object> mandatoryFilter;
    private final FilterExpressionCompiler<T> filterExpressionCompiler;

    public DataSourceLoadSpecification(List<Object> mandatoryFilter, DataSourceLoadOptionsBase dataSourceLoadOptionsBase) {
        this.dataSourceLoadOptionsBase = dataSourceLoadOptionsBase;
        this.mandatoryFilter = mandatoryFilter;
        this.filterExpressionCompiler = new FilterExpressionCompiler<T>();
    }

    public DataSourceLoadSpecification(DataSourceLoadOptionsBase dataSourceLoadOptionsBase) {
        this.dataSourceLoadOptionsBase = dataSourceLoadOptionsBase;
        this.filterExpressionCompiler = new FilterExpressionCompiler<T>();
    }

    public Specification<T> getSpecification() {
        return (root, query, cb) -> {
            query.distinct(dataSourceLoadOptionsBase.isDistinct());
            FilterExpressionCompiler<T> filterExpressionCompiler = new FilterExpressionCompiler<T>();

            Predicate mandatoryPredicate = filterExpressionCompiler.compile(root, cb, mandatoryFilter, JoinType.INNER);
            Predicate filterPredicate = filterExpressionCompiler.compile(root, cb, dataSourceLoadOptionsBase.getFilter(), JoinType.LEFT);

            if (mandatoryPredicate != null && filterPredicate != null) {
                return cb.and(mandatoryPredicate, filterPredicate);
            } else if (filterPredicate != null) {
                return filterPredicate;
            }
            return mandatoryPredicate;
        };
    }

    public Expression<String> getRoot(Root<T> root, String key, JoinType joinType) {
        return filterExpressionCompiler.getRoot(root, key, joinType);
    }

    public Pageable getPageable() {
        var pageAble = new DataSourceLoadPageable(dataSourceLoadOptionsBase);
        return pageAble.getPageable();
    }
}
