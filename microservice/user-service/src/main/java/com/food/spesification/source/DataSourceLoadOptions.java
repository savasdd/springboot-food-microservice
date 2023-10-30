package com.food.spesification.source;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceLoadOptions<T> {

    private Boolean requireTotalCount;
    private String searchOperation;
    private String searchValue;
    private String searchExpr;
    private Integer page;
    private Integer size;
    private Object userData;
    private String stringId;
    private String verifyToken;
    private Long id;
    private boolean distinct = false;
    private boolean isCamelToSnake = false;

    @JsonIgnore
    private ArrayList<Object> mandatoryFilter;
    private List<StoreSortView> sort;
    private List<StoreSortView> sortMock;
    private ArrayList<Object> filter;
    private List<String> select;
    private ArrayList<DataSourceGroup> group;
    private ArrayList<DataSourceGroupView> groupSummary;


    public Pageable getSortable() {
        if (getSort() != null) {
            return PageRequest.of(0, Integer.MAX_VALUE, (getSort().get(0).desc ? Sort.Direction.DESC : Sort.Direction.ASC), getSort().get(0).selector);
        }
        return PageRequest.of(0, Integer.MAX_VALUE);
    }

    public Pageable getMockPageable() {
        if (getPage() == null) {
            setPage(0);
        }

        List<Sort.Order> orderList = new ArrayList<>();
        Sort sort = null;

        if (getSortMock() != null && !getSortMock().isEmpty()) {
            for (StoreSortView item : getSortMock())
                orderList.add(new Sort.Order((item.desc ? Sort.Direction.DESC : Sort.Direction.ASC), (isCamelToSnake ? camelToSnake(item.selector) : item.selector)));
            sort = Sort.by(orderList);
        }
        if (getSize() != null && getSize() > 0 && sort != null) {
            return PageRequest.of((getPage() / getSize()), getSize(), sort);
        } else if (getSize() != null && getSize() > 0 && sort == null) {
            return PageRequest.of((getPage() / getSize()), getSize());
        } else if ((getSize() == null || getSize() == 0) && sort != null) {
            return PageRequest.of(0, Integer.MAX_VALUE, sort);
        }
        return PageRequest.of(0, Integer.MAX_VALUE);
    }

    public Pageable getPageable() {
        if (getPage() == null) {
            setPage(0);
        }

        List<Sort.Order> orderList = new ArrayList<>();
        Sort sort = null;

        if (getSort() != null && !getSort().isEmpty()) {
            for (StoreSortView item : getSort())
                orderList.add(new Sort.Order((item.desc ? Sort.Direction.DESC : Sort.Direction.ASC), (isCamelToSnake ? camelToSnake(item.selector) : item.selector)));
            //orderList.add(new Sort.Order((item.desc? Sort.Direction.DESC:Sort.Direction.ASC),item.selector));
            sort = Sort.by(orderList);
        }
        if (getSize() != null && getSize() > 0 && sort != null) {
            return PageRequest.of((getPage() / getSize()), getSize(), sort);
        } else if (getSize() != null && getSize() > 0 && sort == null) {
            return PageRequest.of((getPage() / getSize()), getSize());
        } else if ((getSize() == null || getSize() == 0) && sort != null) {
            return PageRequest.of(0, Integer.MAX_VALUE, sort);
        }
        return PageRequest.of(0, Integer.MAX_VALUE);
    }

    private Map<String, Join<Object, Object>> joinList = new HashMap<>();

    private Join<Object, Object> getJoin(Root<T> root, String key, JoinType joinType) {
        List<String> values = Arrays.asList(key.split(Pattern.quote(".")));
        List<String> keyList = new ArrayList<>(values);
        keyList.remove(values.size() - 1);
        if (keyList.size() == 0) {
            return null;
        }
        String finalKey = root.getModel().getName() + "|" + String.join(".", keyList) + "|" + root.hashCode();

        StringBuilder jkey = new StringBuilder();
        int index = 0;
        String previousKey = "";
        for (String j : keyList) {
            index++;
            if (index == 1) {
                jkey.append(j);
            } else {
                previousKey = root.getModel().getName() + "|" + jkey.toString() + "|" + root.hashCode();
                jkey.append(".").append(j);
            }
            String allKey = root.getModel().getName() + "|" + jkey.toString() + "|" + root.hashCode();
            if (!joinList.containsKey(allKey)) {
                if (index == 1) {
                    joinList.put(allKey, root.join(j, joinType));
                } else {
                    joinList.put(allKey, joinList.get(previousKey).join(j, joinType));
                }
            }

        }
        return joinList.get(finalKey);
    }

    private Expression<String> getRootMandatory(Root<T> root, String key, CriteriaBuilder cb) {
        List<String> values = Arrays.asList(key.split(Pattern.quote(".")));
        if (values.size() == 1) {
            return root.get(values.get(0));
        }
        Join<Object, Object> joinMandatory = getJoin(root, key, JoinType.INNER);
        return joinMandatory.get(values.get(values.size() - 1));
    }


    public Expression<String> getRoot(Root<T> root, String key) {
        List<String> values = Arrays.asList(key.split(Pattern.quote(".")));
        if (values.size() == 1) {
            return root.get(values.get(0));
        }
        Join<Object, Object> join = getJoin(root, key, JoinType.LEFT);
        return join.get(values.get(values.size() - 1));
    }

    public Specification<T> toSpecification() {
        return toSpecification(true);
    }

    public Specification<T> toSpecification(Boolean mandatory) {
        return (Specification<T>) (root, query, cb) -> {
            //region force
            Predicate finalPredicate = null;
            if (distinct) {
                query.distinct(distinct);
            }
            if (getMandatoryFilter() != null) {
                finalPredicate = getPredicate(root, cb, getMandatoryFilter(), mandatory, "and");
            }
            //endregion

            //region filter
            if (getSearchValue() != null && !getSearchValue().isEmpty()) {
                ArrayList<Object> finalValue = new ArrayList<>();
                ArrayList<Object> addValue = new ArrayList<>();
                addValue.add(getSearchExpr());
                addValue.add(getSearchOperation());
                addValue.add(getSearchValue());
                finalValue.add(addValue);
                if (getFilter() != null) {
                    finalValue.add("and");
                    finalValue.add(getFilter());
                }
                Predicate finalCustomPredicate = getPredicate(root, cb, finalValue, false, "and");
                if (finalPredicate != null && finalCustomPredicate != null) {
                    finalPredicate = cb.and(finalPredicate, finalCustomPredicate);
                } else if (finalCustomPredicate != null) {
                    finalPredicate = finalCustomPredicate;
                }
                return finalPredicate;
            }
            if (getFilter() != null) {
                Predicate finalCustomPredicate = getPredicate(root, cb, getFilter(), false, "and");
                if (finalPredicate != null && finalCustomPredicate != null) {
                    finalPredicate = cb.and(finalPredicate, finalCustomPredicate);
                } else if (finalCustomPredicate != null) {
                    finalPredicate = finalCustomPredicate;
                }
            }
            //endregion
            return finalPredicate;
        };
    }

    private Predicate getPredicate(Root root, CriteriaBuilder cb, ArrayList<Object> filterList, boolean mandatory, String caseParent) {
        Predicate finalCustomPredicate = null;
        Predicate predicateTemp;

        for (int i = 0; i < filterList.size(); i++) {
            Object tp = filterList.get(i);
            if (!(tp instanceof ArrayList)) {
                if (i == 0)
                    tp = filter;
                else
                    continue;
            }
            try {
                if (tp instanceof ArrayList && ((ArrayList) tp).get(1) instanceof String && ((ArrayList) tp).get(0) instanceof ArrayList && ((ArrayList) tp).get(2) instanceof ArrayList) {
                    if (finalCustomPredicate == null)
                        finalCustomPredicate = getPredicate(root, cb, (ArrayList<Object>) tp, mandatory, (String) ((ArrayList) tp).get(1));
                    else {
                        if (caseParent.equalsIgnoreCase("and"))
                            finalCustomPredicate = cb.and(finalCustomPredicate, getPredicate(root, cb, (ArrayList<Object>) tp, mandatory, (String) ((ArrayList) tp).get(1)));
                        else if (caseParent.equalsIgnoreCase("or"))
                            finalCustomPredicate = cb.or(finalCustomPredicate, getPredicate(root, cb, (ArrayList<Object>) tp, mandatory, (String) ((ArrayList) tp).get(1)));
                        else
                            finalCustomPredicate = cb.and(finalCustomPredicate, getPredicate(root, cb, (ArrayList<Object>) tp, mandatory, (String) ((ArrayList) tp).get(1)));
                    }
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String label = String.valueOf(((ArrayList) tp).get(0));
            String state = String.valueOf(((ArrayList) tp).get(1));
            String value1 = String.valueOf(((ArrayList) tp).get(2));
            predicateTemp = getPredicate(root, cb, label, value1, state, mandatory);

            if (finalCustomPredicate == null) {
                finalCustomPredicate = predicateTemp;
            } else {
                String caseState = (String) filterList.get(filterList.size() - 2);
                if (caseState.equalsIgnoreCase("and"))
                    finalCustomPredicate = cb.and(finalCustomPredicate, predicateTemp);
                else if (caseState.equalsIgnoreCase("or"))
                    finalCustomPredicate = cb.or(finalCustomPredicate, predicateTemp);
                else
                    finalCustomPredicate = cb.and(finalCustomPredicate, predicateTemp);

            }
        }
        return finalCustomPredicate;
    }

    private Predicate getPredicate(Root<T> root, CriteriaBuilder cb, String label, String value, String state, boolean mandatory) {
        if (label == null || label.isEmpty() || value == null || value.isEmpty() || state == null || state.isEmpty()) {
            return null;
        }
        List<Predicate> predicatesFilter = new ArrayList<>();
        Expression<String> expression = mandatory ? getRootMandatory(root, label, cb) : getRoot(root, label);
        if (expression != null) {
            Predicate predicate = getPredicate(cb, expression, state, value);
            if (predicate != null) {
                predicatesFilter.add(predicate);
                return cb.or(predicatesFilter.toArray(new Predicate[0]));
            }
        }
        return null;
    }

    private Predicate getPredicate(CriteriaBuilder cb, Expression<String> expression, String state, String value) {
        Comparable comparableValue;
        if (Objects.requireNonNull(expression).getJavaType().equals(Date.class) && !value.equals("null")) {
            try {
                comparableValue = new DateTime(Long.valueOf(value)).toDate();
            } catch (NumberFormatException ex) {
                comparableValue = new DateTime(value).toDate();
            }
        } else if (Objects.requireNonNull(expression).getJavaType().equals(String.class) || Objects.requireNonNull(expression).getJavaType().equals(Long.class) || Objects.requireNonNull(expression).getJavaType().equals(Integer.class) || Objects.requireNonNull(expression).getJavaType().equals(BigInteger.class) || Objects.requireNonNull(expression).getJavaType().equals(Double.class)) {
            comparableValue = value.replace("İ", "i").replace("I", "ı").toLowerCase();
        } else if (Objects.requireNonNull(expression).getJavaType().equals(Boolean.class) || Objects.requireNonNull(expression).getJavaType().equals(boolean.class)) {
            comparableValue = Boolean.valueOf(value);
        } else if (Objects.requireNonNull(expression).getJavaType().isEnum()) {
            try {
                comparableValue = findEnumValue(ReflectHelper.classForName(expression.getJavaType().getTypeName(), this.getClass()), value);
            } catch (ClassNotFoundException ignored) {
                return null;
            }
        } else if (value.equals("null")) {
            comparableValue = value;
        } else {
            return null;
        }

        Path expressionPath = (Path) expression;

        switch (state) {
            case ">":
                return cb.greaterThan(expressionPath, comparableValue);
            case ">=":
                return cb.greaterThanOrEqualTo(expressionPath, comparableValue);
            case "<":
                return cb.lessThan(expressionPath, comparableValue);
            case "<=":
                return cb.lessThanOrEqualTo(expressionPath, comparableValue);
            case "=":
                if (comparableValue == null || comparableValue.equals("null")) {
                    return cb.isNull(expressionPath);
                }
                if (Objects.requireNonNull(expression).getJavaType().equals(String.class)) {
                    if (!((SingularAttributePath) expressionPath).getAttribute().getName().equals("searchCode")) {
                        return cb.equal(cb.lower(expressionPath), comparableValue);
                    }
                    return cb.equal(expressionPath, comparableValue);
                }
                return cb.equal(expressionPath, comparableValue);
            case "contains":
                return cb.like(cb.lower(expressionPath), ("%" + comparableValue + "%"));
            case "notcontains":
                return cb.notLike(cb.lower(expressionPath), ("%" + comparableValue + "%"));
            case "startswith":
                if (!((SingularAttributePath) expressionPath).getAttribute().getName().equals("searchCode")) {
                    return cb.like(cb.lower(expressionPath), (comparableValue + "%"));
                }
                return cb.like(expressionPath, (comparableValue + "%"));
            case "endswith":
                return cb.like(cb.lower(expressionPath), ("%" + comparableValue));
            case "<>":
                if (comparableValue == null || comparableValue.equals("null")) {
                    return cb.isNotNull(expressionPath);
                }
                return cb.notEqual(expressionPath, comparableValue);
        }
        return null;
    }

    private static Enum<?> findEnumValue(Class<? extends Enum<?>> enumType, String value) {
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.name().equals(value))
                .findFirst()
                .orElse(null);
    }

    public String camelToSnake(String value) {
        final char[] name = value.toCharArray();
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < name.length; i++) {
            if (Character.isUpperCase(name[i]) || name[i] == '.' || name[i] == '$') {
                if (i != 0 && name[i - 1] != '.' && name[i - 1] != '$') {
                    builder.append('_');
                }
                if (name[i] != '.' && name[i] != '$') {
                    builder.append(Character.toLowerCase(name[i]));
                }
            } else {
                builder.append(name[i]);
            }
        }

        return builder.toString();
    }
}
