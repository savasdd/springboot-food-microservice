package com.food.data.expression;

import org.hibernate.internal.util.ReflectHelper;

import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;


public class FilterExpressionCompiler<T> {

    public Predicate compile(Root<T> root, CriteriaBuilder cb, List<?> criteriaJson, JoinType joinType) {
        if (criteriaJson == null || criteriaJson.isEmpty()) {
            return null;
        }
        return compileCore(root, cb, criteriaJson, joinType);
    }

    public Predicate compileCore(Root<T> root, CriteriaBuilder cb, List<?> criteriaJson, JoinType joinType) {
        if (isCriteria(criteriaJson.get(0))) {
            return compileGroup(root, cb, criteriaJson, joinType);
        }

        if (isUnary(criteriaJson)) {
            return compileUnary(root, cb, criteriaJson, joinType);
        }

        return compileBinary(root, cb, criteriaJson, joinType);
    }

    private Predicate compileGroup(Root<T> root, CriteriaBuilder cb, List<?> criteriaJson, JoinType joinType) {
        List<Predicate> operands = new ArrayList<>();
        var isAnd = true;
        var nextIsAnd = true;
        for (var item : criteriaJson) {
            if (isCriteria(item)) {
                if (operands.size() > 1 && isAnd != nextIsAnd) {
                    throw new RuntimeException("Mixing of and/or is not allowed inside a single group");
                }

                isAnd = nextIsAnd;
                operands.add(compileCore(root, cb, (List<?>) item, joinType));
                nextIsAnd = true;
            } else {
                nextIsAnd = Objects.equals(item, "and") || Objects.equals(item, "&");
            }
        }
        Predicate result = null;
        for (var operand : operands) {
            if (result == null)
                result = operand;
            else if (isAnd) {
                result = cb.and(result, operand);
            } else {
                result = cb.or(result, operand);
            }
        }
        return result;
    }

    private Predicate compileUnary(Root<T> root, CriteriaBuilder cb, List<?> criteriaJson, JoinType joinType) {
        return cb.not(compileCore(root, cb, (List<?>) criteriaJson.get(1), joinType));
    }

    private Predicate compileBinary(Root<T> root, CriteriaBuilder cb, List<?> criteriaJson, JoinType joinType) {
        var hasExplicitOperation = criteriaJson.size() > 2;

        String clientAccessor = (String) criteriaJson.get(0);
        var clientOperation = hasExplicitOperation ? (String) criteriaJson.get(1) : "=";
        String clientValue = String.valueOf(criteriaJson.get(hasExplicitOperation ? 2 : 1));
        return getPredicate(root, cb, clientAccessor, clientValue, clientOperation, joinType);
    }

    private boolean isCriteria(Object item) {
        return item instanceof List;
    }

    private boolean isUnary(List<?> criteriaJson) {
        return Objects.equals(criteriaJson.get(0), "!");
    }

    private Predicate getPredicate(Root<T> root, CriteriaBuilder cb, String label, String value, String state, JoinType joinType) {
        if (label == null || label.isEmpty() || value == null || value.isEmpty() || state == null || state.isEmpty()) {
            return null;
        }
        Expression<String> expression = getRoot(root, label, joinType);
        if (expression != null) {
            return getPredicate(cb, expression, state, value);
        }
        return null;
    }

    public Expression<String> getRoot(Root<T> root, String key, JoinType joinType) {
        List<String> values = Arrays.asList(key.split(Pattern.quote(".")));
        if (values.size() == 1) {
            return root.get(values.get(0));
        }
        Join<Object, Object> join = getJoin(root, key, joinType);
        return join != null ? join.get(values.get(values.size() - 1)) : null;
    }

    private final Map<String, Join<Object, Object>> joinList = new HashMap<>();

    private Join<Object, Object> getJoin(Root<T> root, String key, JoinType joinType) {
        List<String> values = Arrays.asList(key.split(Pattern.quote(".")));
        List<String> keyList = new ArrayList<>(values);
        keyList.remove(values.size() - 1);
        if (keyList.size() == 0) {
            return null;
        }
        String finalKey = root.getModel().getName() + "|" + String.join(".", keyList) + "|" + root.hashCode() + "|" + joinType.name();
        ;

        StringBuilder jkey = new StringBuilder();
        int index = 0;
        String previousKey = "";
        for (String j : keyList) {
            index++;
            if (index == 1) {
                jkey.append(j);
            } else {
                previousKey = root.getModel().getName() + "|" + jkey + "|" + root.hashCode() + "|" + joinType.name();
                jkey.append(".").append(j);
            }
            String allKey = root.getModel().getName() + "|" + jkey + "|" + root.hashCode() + "|" + joinType.name();
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

    private Predicate getPredicate(CriteriaBuilder cb, Expression<String> expression, String state, String value) {
        Comparable comparableValue;
        if (Objects.requireNonNull(expression).getJavaType().getName().equals(Date.class.getName())) {
            comparableValue = (value != null && !Objects.equals(value, "null")) ? new Date(Long.parseLong(value)) : null;
        } else if (Objects.requireNonNull(expression).getJavaType().equals(String.class)) {
            comparableValue = value.replace("İ", "i").replace("I", "ı").toLowerCase();
        } else if (Objects.requireNonNull(expression).getJavaType().getName().equals(Long.class.getName())
                || Objects.requireNonNull(expression).getJavaType().getName().equals(Integer.class.getName())
                || Objects.requireNonNull(expression).getJavaType().getName().equals(BigInteger.class.getName())
                || Objects.requireNonNull(expression).getJavaType().getName().equals(Double.class.getName())
        ) {
            comparableValue = value;
        } else if (Objects.requireNonNull(expression).getJavaType().getName().equals(Boolean.class.getName())
                || Objects.requireNonNull(expression).getJavaType().getName().equals(boolean.class.getName())) {
            comparableValue = (value != null && !Objects.equals(value, "null")) ? Boolean.valueOf(value) : null;
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
            case ">" -> {
                return cb.greaterThan(expressionPath, comparableValue);
            }
            case ">=" -> {
                return cb.greaterThanOrEqualTo(expressionPath, comparableValue);
            }
            case "<" -> {
                return cb.lessThan(expressionPath, comparableValue);
            }
            case "<=" -> {
                return cb.lessThanOrEqualTo(expressionPath, comparableValue);
            }
            case "=" -> {
                if (comparableValue == null || comparableValue.equals("null")) {
                    return cb.isNull(expressionPath);
                }
                if (Objects.requireNonNull(expression).getJavaType().equals(String.class)) {
                    return cb.equal(cb.lower(expressionPath), comparableValue);
                }
                return cb.equal(expressionPath, comparableValue);
            }
            case "contains" -> {
                return cb.like(cb.lower(expressionPath), ("%" + comparableValue + "%"));
            }
            case "notcontains" -> {
                return cb.notLike(cb.lower(expressionPath), ("%" + comparableValue + "%"));
            }
            case "startswith" -> {
                return cb.like(cb.lower(expressionPath), (comparableValue + "%"));
            }
            case "endswith" -> {
                return cb.like(cb.lower(expressionPath), ("%" + comparableValue));
            }
            case "<>" -> {
                if (comparableValue == null || comparableValue.equals("null")) {
                    return cb.isNotNull(expressionPath);
                }
                return cb.notEqual(expressionPath, comparableValue);
            }
        }
        throw new RuntimeException("Not found state");
//        return null;
    }

    private static Enum<?> findEnumValue(Class<? extends Enum<?>> enumType, String value) {
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.name().equals(value))
                .findFirst()
                .orElse(null);
    }
}
