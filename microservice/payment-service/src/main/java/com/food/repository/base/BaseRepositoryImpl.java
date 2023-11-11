package com.food.repository.base;

import com.food.data.options.DataSourceLoadOptionsBase;
import com.food.data.options.GroupingInfo;
import com.food.data.options.implementation.DataSourceLoadSpecification;
import com.food.data.response.LoadResult;
import com.vladmihalcea.hibernate.query.SQLExtractor;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Transactional(readOnly = true)
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private final EntityManager entityManager;
    private List<Order> orders = new ArrayList<>();
    private List<Selection<?>> selectionList = new ArrayList<>();
    private List<Expression<?>> groupList = new ArrayList<>();

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public LoadResult load(DataSourceLoadOptionsBase options) {
        if ((options.getGroup() != null && options.getGroup().length > 0) || (options.getSelect() != null && options.getSelect().length > 0)) {
            return loadTuple(options);
        } else {
            return loadEntity(options);
        }
    }

    @Override
    public LoadResult loadEntity(DataSourceLoadOptionsBase options) {
        setPrimaryKey(options);
        setFinalFilter(options);

        var loadResult = new LoadResult();
        var ds = new DataSourceLoadSpecification<T>(options.getMandatoryFilter(), options);
        if (options.getTake() > 0) {
            loadResult.setItems(findAll(ds.getSpecification(), ds.getPageable()).getContent());
        }
        if (options.isRequireTotalCount()) {
            loadResult.setTotalCount(count(ds.getSpecification()));
        }
        return loadResult;
    }

    @Override
    public LoadResult loadTuple(DataSourceLoadOptionsBase options) {
        setPrimaryKey(options);
        setFinalFilter(options);

        if (options.getGroup() != null) {
            return loadGroup(options);
        } else {
            return loadDefault(options);
        }
    }

    @Override
    public String getSqlQuery(DataSourceLoadOptionsBase options) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> query = criteriaBuilder.createQuery(getDomainClass());
        final Root<T> root = query.from(getDomainClass());
        var ds = new DataSourceLoadSpecification<T>(options.getMandatoryFilter(), options);
        Predicate predicate = ds.getSpecification().toPredicate(root, query, criteriaBuilder);
        if (predicate != null) {
            query.where(predicate);
            Query criteriaQuery = entityManager.createQuery(query);
            return SQLExtractor.from(criteriaQuery);
        }
        return null;
    }

    private LoadResult loadDefault(DataSourceLoadOptionsBase options) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        final Root<T> root = query.from(getDomainClass());
        var ds = new DataSourceLoadSpecification<T>(options.getMandatoryFilter(), options);

        BaseTupleResult<T> baseResult = new BaseTupleResult<>(entityManager, getDomainClass());
        var loadResult = new LoadResult();

        if (options.getTake() > 0) {
            loadResult.setItems(baseResult.getResultList(options.getMandatoryFilter(), options));
        }

        if (options.isRequireTotalCount()) {
            loadResult.setTotalCount(getTotalCount(criteriaBuilder, root, ds));
        }
        return loadResult;
    }

    private LoadResult loadGroup(DataSourceLoadOptionsBase options) {
        var loadResult = new LoadResult();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        final Root<T> root = query.from(getDomainClass());
        var ds = new DataSourceLoadSpecification<T>(options.getMandatoryFilter(), options);
        Predicate predicate = ds.getSpecification().toPredicate(root, query, criteriaBuilder);
        if (predicate != null) {
            query.where(predicate);
        }
        setGroups(criteriaBuilder, root, ds, options.getGroup());
        query.groupBy(groupList);
        if (selectionList.isEmpty()) {
            return null;
        }

        query.multiselect(selectionList);

        if (!orders.isEmpty()) {
            query.orderBy(orders);
        }
        int skip = options.getSkip();
        int take = options.getTake() > 0 ? options.getTake() : Integer.MAX_VALUE;
        final List<Tuple> resultList = entityManager.createQuery(query)
                .setFirstResult(skip)
                .setMaxResults(take)
                .getResultList();
        loadResult.setItems(toGroupJson(resultList));

        loadResult.setTotalCount(getTotalCount(criteriaBuilder, root, ds));
        loadResult.setGroupCount(getGroupCount(criteriaBuilder, ds, options));
        return loadResult;
    }

    private Long getTotalCount(CriteriaBuilder criteriaBuilder, Root<T> root, DataSourceLoadSpecification<T> ds) {
        final CriteriaQuery<Long> queryCount = criteriaBuilder.createQuery(Long.class);
        final Root<T> rootCount = queryCount.from(getDomainClass());
        queryCount.select(criteriaBuilder.count(rootCount));
        var predicate = ds.getSpecification().toPredicate(rootCount, queryCount, criteriaBuilder);
        if (predicate != null) {
            queryCount.where(predicate);
        }
        return entityManager.createQuery(queryCount).getSingleResult();
    }

    private Long getGroupCount(CriteriaBuilder criteriaBuilder, DataSourceLoadSpecification<T> ds, DataSourceLoadOptionsBase options) {
        final CriteriaQuery<Tuple> queryCount = criteriaBuilder.createQuery(Tuple.class);
        final Root<T> rootCount = queryCount.from(getDomainClass());
        Expression<String> selection = ds.getRoot(rootCount, options.getGroup()[0].getSelector(), JoinType.LEFT);
        queryCount.multiselect(criteriaBuilder.countDistinct(selection));
        var predicate = ds.getSpecification().toPredicate(rootCount, queryCount, criteriaBuilder);
        if (predicate != null) {
            queryCount.where(predicate);
        }
        return entityManager.createQuery(queryCount).getSingleResult().get(0, Long.class);
    }

    private Object toGroupJson(List<Tuple> results) {
        List<JSONObject> json = new ArrayList<>();
        HashMap<String, Object> map;
        for (Tuple tuple : results) {
            List<TupleElement<?>> cols = tuple.getElements();
            map = new HashMap<>();
            for (TupleElement<?> col : cols) {
                if (col instanceof SingularAttributePath<?>) {
                    var value = tuple.get(col);
                    if (value != null) {
                        if (!map.containsKey("key")) {
                            map.put("key", value);
                        }
                    }
                } else if (col instanceof AggregationFunction<?>) {
                    var value = tuple.get(col);
                    if (!map.containsKey("count")) {
                        map.put("count", value);
                        map.put("summary", List.of(value));
                    }
                }

                map.put("items", "");
            }
            JSONObject node = new JSONObject(map);
            json.add(node);
        }

        return json;
    }

    private void setGroups(CriteriaBuilder criteriaBuilder, Root<T> root, DataSourceLoadSpecification<T> ds, GroupingInfo[] group) {
        orders = new ArrayList<>();
        selectionList = new ArrayList<>();
        groupList = new ArrayList<>();
        for (GroupingInfo property : group) {
            Expression<String> data = ds.getRoot(root, property.getSelector(), JoinType.LEFT);
            selectionList.add(data);
            if (property.getDesc() != null && property.getDesc()) {
                orders.add(criteriaBuilder.desc(data));
            } else {
                orders.add(criteriaBuilder.asc(data));
            }
            selectionList.add(criteriaBuilder.count(data));
            groupList.add(data);
        }
    }

    private void setFinalFilter(DataSourceLoadOptionsBase options) {
        if (options.getSearchValue() != null && !options.getSearchValue().isEmpty()) {
            ArrayList<Object> finalValue = new ArrayList<>();
            if (options.getFilter() != null) {
                if (options.getFilter() instanceof ArrayList) {
                    if (options.getFilter().get(0) instanceof ArrayList) {
                        finalValue = (ArrayList<Object>) options.getFilter();
                        finalValue.add("and");
                    } else {
                        finalValue.add(options.getFilter());
                        finalValue.add("and");
                    }
                }
            }
            ArrayList<Object> addValue = new ArrayList<>();
            if (options.getSearchExpr() instanceof ArrayList) {
                ArrayList<Object> arrayValue = new ArrayList<>();
                for (Object obj : (ArrayList<?>) options.getSearchExpr()) {
                    addValue = new ArrayList<>();
                    addValue.add(obj);
                    addValue.add(options.getSearchOperation());
                    addValue.add(options.getSearchValue());
                    if (arrayValue.size() > 0) {
                        arrayValue.add("or");
                        arrayValue.add(addValue);
                    } else {
                        arrayValue.add(addValue);
                    }
                }
                finalValue.add(arrayValue);
            } else {
                addValue.add(options.getSearchExpr());
                addValue.add(options.getSearchOperation());
                addValue.add(options.getSearchValue());
                finalValue.add(addValue);
            }
            options.setFilter(finalValue);
        }
    }

    private String getPrimaryKey() {
        for (Field item : getDomainClass().getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isStatic(item.getModifiers())) {
                if (item.isAnnotationPresent(Id.class)) {
                    return item.getName();
                }
            }
        }

        for (Field item : getDomainClass().getSuperclass().getDeclaredFields()) {
            if (!(java.lang.reflect.Modifier.isStatic(item.getModifiers()) || java.lang.reflect.Modifier.isTransient(item.getModifiers()))) {

                if (item.isAnnotationPresent(Id.class)) {
                    return item.getName();
                }
            }
        }
        return null;
    }

    private void setPrimaryKey(DataSourceLoadOptionsBase options) {
        if (options != null && options.getPrimaryKey() == null) {
            options.setPrimaryKey(new String[]{
                    getPrimaryKey()
            });
        }
    }

}
