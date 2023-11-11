package com.food.repository.base;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.data.filter.EOperator;
import com.food.data.filter.Filter;
import com.food.data.options.DataSourceLoadOptions;
import com.food.data.options.DataSourceLoadOptionsBase;
import com.food.data.options.SortingInfo;
import com.food.data.options.implementation.DataSourceLoadSpecification;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.hibernate.query.criteria.internal.path.RootImpl;
import org.hibernate.query.criteria.internal.path.SingularAttributeJoin;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;


public class BaseTupleResult<T> {

    private final EntityManager entityManager;
    private List<Order> orders = new ArrayList<>();
    private List<Selection<?>> selectionList = new ArrayList<>();
    private final Map<String, Selection<?>> pluralSelectionMap = new HashMap<>();
    private final Map<String, MultipleField> multipleFieldList = new HashMap<>();
    private final Class<T> clazz;

    public BaseTupleResult(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    public List<JSONObject> getResultList(List<Object> filter, DataSourceLoadOptionsBase options) {
        var allFields = getFields(options);
//        TODO approve
//        setPrimaryKey(options);
//        setFinalFilter(options);

        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);
        final Root<T> root = query.from(clazz);
        var ds = new DataSourceLoadSpecification<T>(filter, options);
        Predicate predicate = ds.getSpecification().toPredicate(root, query, criteriaBuilder);
        if (predicate != null) {
            query.where(predicate);
        }

        setOrders(criteriaBuilder, root, ds, options.getSort());
        setSelectionList(root, ds, allFields);

        if (selectionList.size() == 0) {
            return null;
        }

        query.multiselect(selectionList);

//        if (options.getSort() == null || options.getSort().length == 0) {
//            query.distinct(true);
//        }
        query.distinct(options.isDistinct());

        if (orders.size() > 0) {
            query.orderBy(orders);
        }

        int skip = options.getSkip();
        int take = options.getTake() > 0 ? options.getTake() : Integer.MAX_VALUE;
        List<Tuple> tupleList = entityManager.createQuery(query)
                .setFirstResult(skip)
                .setMaxResults(take)
                .getResultList();

        HashMap<String, Object> map;
        List<JSONObject> json = new ArrayList<>();
        for (Tuple tuple : tupleList) {
            Optional<TupleElement<?>> idTupleElement = tuple.getElements().stream().filter(f -> f.getAlias().equalsIgnoreCase(getPrimaryKey())).findFirst();
            Long primaryKey = idTupleElement.map(tupleElement -> (Long) tuple.get(tupleElement)).orElse(null);
            if (primaryKey != null) {
                List<TupleElement<?>> cols = tuple.getElements();
                map = new HashMap<>();
                for (TupleElement<?> col : cols) {
                    String key = col.getAlias();
                    var value = tuple.get(col);
                    var splitSelect = key.split(Pattern.quote("."));
                    if (splitSelect.length == 1) {
                        map.put(key, value);
                    } else {
                        addPut(map, key, value);
                    }
                }
                if (pluralSelectionMap.size() > 0) {
                    for (var entry : pluralSelectionMap.entrySet()) {
                        String key = entry.getKey();
                        Selection<?> selection = entry.getValue();
                        if (selection instanceof PluralAttributePath<?>) {
                            var clz = ((PluralAttributePath<?>) selection).getAttribute().getBindableJavaType();
                            String joinKey = getMappedKey((PluralAttributePath<?>) selection);
                            var flt = Filter.build(Object.class)
                                    .operation(EOperator.equal)
                                    .custom(joinKey + "." + getPrimaryKey(), primaryKey);
                            var baseResult = new BaseTupleResult<>(entityManager, clz);
                            var result = baseResult.getResultList(flt.get(), new DataSourceLoadOptionsBase());
                            map.put(key, result);
                        }
                    }
                }
                if (multipleFieldList.size() > 0) {
                    for (var entry : multipleFieldList.entrySet()) {
                        String key = entry.getKey();
                        Selection<?> selection = entry.getValue().getSelection();
                        if (selection instanceof PluralAttributePath<?>) {
                            var clz = ((PluralAttributePath<?>) selection).getAttribute().getBindableJavaType();
                            String joinKey = getMappedKey((PluralAttributePath<?>) selection);
                            var flt = Filter.build(Object.class)
                                    .operation(EOperator.equal)
                                    .custom(joinKey + "." + getPrimaryKey(), primaryKey);
                            var baseResult = new BaseTupleResult<>(entityManager, clz);
                            DataSourceLoadOptions dataSourceLoadOptions = new DataSourceLoadOptions();
                            dataSourceLoadOptions.setSelect(entry.getValue().getSelect().toArray(new String[0]));
                            var result = baseResult.getResultList(flt.get(), dataSourceLoadOptions);
                            map.put(key, result);
                        }
                    }
                }
                json.add(new JSONObject(map));
            }
        }
        return json;
    }

    private List<String> getFields(DataSourceLoadOptionsBase optionsBase) {
        String[] select = optionsBase.getSelect();
        String pkField = null;
        List<String> fields = new ArrayList<>();
        for (Field item : clazz.getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isStatic(item.getModifiers())) {
                if (item.isAnnotationPresent(Id.class)) {
                    pkField = item.getName();
                }
                if (!(item.isAnnotationPresent(JsonIgnore.class)
                        || item.isAnnotationPresent(Transient.class)
                        || item.isAnnotationPresent(JsonBackReference.class)
                )) {
                    if (!item.isAnnotationPresent(JsonProperty.class)) {
                        fields.add(item.getName());
                    } else {
                        var jsonProperty = item.getAnnotation(JsonProperty.class);
                        if (jsonProperty.access() != JsonProperty.Access.WRITE_ONLY) {
                            fields.add(item.getName());
                        }
                    }
                }
            }
        }

        for (Field item : clazz.getSuperclass().getDeclaredFields()) {
            if (!(java.lang.reflect.Modifier.isStatic(item.getModifiers()) || java.lang.reflect.Modifier.isTransient(item.getModifiers()))) {

                if (item.isAnnotationPresent(Id.class)) {
                    pkField = item.getName();
                }

                if (!(item.isAnnotationPresent(JsonIgnore.class)
                        || item.isAnnotationPresent(Transient.class)
                        || item.isAnnotationPresent(JsonBackReference.class)
                )) {
                    if (!item.isAnnotationPresent(JsonProperty.class)) {
                        fields.add(item.getName());
                    } else {
                        var jsonProperty = item.getAnnotation(JsonProperty.class);
                        if (jsonProperty.access() != JsonProperty.Access.WRITE_ONLY) {
                            fields.add(item.getName());
                        }
                    }
                }
            }
        }

        if (select == null || select.length == 0) {
            return fields;
        }

        String finalPkField = pkField;
        if (Arrays.stream(select).noneMatch(f -> finalPkField != null && Objects.equals(f, finalPkField))) {
            var selectList = Arrays.stream(select).collect(toList());
            selectList.add(finalPkField);
            select = selectList.toArray(new String[0]);
        }

        String xmin = "xmin";
        if (Arrays.stream(select).noneMatch(f -> Objects.equals(f, xmin))) {
            var selectList = Arrays.stream(select).collect(toList());
            selectList.add(xmin);
            select = selectList.toArray(new String[0]);
        }

        return Arrays.stream(select).filter(f ->
                fields.contains(getRootField(f))
        ).toList();
    }

    private void setSelectionList(Root<T> root, DataSourceLoadSpecification<T> ds, List<String> allFields) {
        selectionList = new ArrayList<>();
        for (String item : allFields) {
            var itemList = item.split(Pattern.quote("."));
            if (itemList.length == 1) {
                Selection<?> selection = ds.getRoot(root, item, JoinType.LEFT).alias(item);
                if (selection instanceof PluralAttributePath<?>) {
                    pluralSelectionMap.put(item, selection);
                } else if (selection instanceof SingularAttributePath<?>) {
                    if (((SingularAttributePath<?>) selection).getPathSource() instanceof RootImpl<?>) {
                        selectionList.add(selection);
                    } else if (((SingularAttributePath<?>) selection).getPathSource() instanceof SingularAttributeJoin) {
                        selectionList.add(selection);
                    } else {
                        selectionList.add(selection);
                    }
                } else {
                    selectionList.add(selection);
                }
            } else if (itemList.length > 1) {
                Selection<?> selection = ds.getRoot(root, itemList[0], JoinType.LEFT).alias(itemList[0]);
                if (selection instanceof SingularAttributePath<?>) {
                    selection = ds.getRoot(root, item, JoinType.LEFT).alias(item);
                    selectionList.add(selection);
                    continue;
                }
                List<String> value;
                if (multipleFieldList.containsKey(itemList[0])) {
                    value = multipleFieldList.get(itemList[0]).getSelect();
                } else {
                    value = new ArrayList<>();
                }
                value.add(String.join(".", Arrays.copyOfRange(itemList, 1, itemList.length)));
                multipleFieldList.put(itemList[0], MultipleField.builder()
                        .select(value)
                        .selection(selection)
                        .build());
            }
        }
    }

    private String getRootField(String item) {
        var splitSelect = item.split(Pattern.quote("."));
        if (splitSelect.length > 1) {
            item = splitSelect[0];
        }
        return item;
    }

    private void setOrders(CriteriaBuilder criteriaBuilder, Root<T> root, DataSourceLoadSpecification<T> ds, SortingInfo[] sort) {
        orders = new ArrayList<>();
        if (sort != null) {
            for (SortingInfo property : sort) {
                Expression<String> data = ds.getRoot(root, property.getSelector(), JoinType.LEFT);
                if (property.getDesc()) {
                    orders.add(criteriaBuilder.desc(data));
                } else {
                    orders.add(criteriaBuilder.asc(data));
                }
            }
        }
    }

    private void setPrimaryKey(DataSourceLoadOptionsBase options) {
        if (options != null && options.getPrimaryKey() == null) {
            options.setPrimaryKey(new String[]{
                    getPrimaryKey()
            });
        }
    }

    private String getPrimaryKey() {
        for (Field item : clazz.getDeclaredFields()) {
            if (!java.lang.reflect.Modifier.isStatic(item.getModifiers())) {
                if (item.isAnnotationPresent(Id.class)) {
                    return item.getName();
                }
            }
        }

        for (Field item : clazz.getSuperclass().getDeclaredFields()) {
            if (!(java.lang.reflect.Modifier.isStatic(item.getModifiers()) || java.lang.reflect.Modifier.isTransient(item.getModifiers()))) {

                if (item.isAnnotationPresent(Id.class)) {
                    return item.getName();
                }
            }
        }
        return null;
    }

    private void setFinalFilter(DataSourceLoadOptionsBase options) {
        if (options.getSearchValue() != null && !options.getSearchValue().isEmpty()) {
            ArrayList<Object> finalValue = new ArrayList<>();
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

    private String getMappedKey(PluralAttributePath<?> pluralAttributePath) {
        String joinKey = pluralAttributePath.getPersister().getMappedByProperty();
        if (joinKey != null) {
            return joinKey;
        }
        Class<?> clz = pluralAttributePath.getAttribute().getBindableJavaType();
        for (Field item : clz.getDeclaredFields()) {
            if (item.getType().getTypeName().equals(clazz.getTypeName())) {
                return item.getName();
            }
        }

        for (Field item : clz.getSuperclass().getDeclaredFields()) {
            if (item.getType().getTypeName().equals(clazz.getTypeName())) {
                return item.getName();
            }
        }

        return null;
    }

    private Object addPut(HashMap<String, Object> map, String key, Object value) {
        var splitSelect = key.split(Pattern.quote("."));
        if (splitSelect.length > 1) {
            HashMap<String, Object> tempMap = map.containsKey(splitSelect[0]) ? (HashMap<String, Object>) map.get(splitSelect[0]) : new HashMap<>();
            map.put(splitSelect[0], addPut(tempMap, String.join(".", Arrays.copyOfRange(splitSelect, 1, splitSelect.length)), value));
        } else {
            map.put(key, value);
        }
        return map;
    }

}
