package com.food.data.expression;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.compare.ComparableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class BooleanExpressionCompiler {
    public static boolean compile(Map<String, Object> data, List<Object> criteriaJson) {
        if (criteriaJson == null || criteriaJson.isEmpty()) {
            return false;
        }
        return compileCore(data, criteriaJson);
    }

    public static Boolean compileCore(Map<String, Object> data, List<Object> criteriaJson) {
        if (isCriteria(criteriaJson.get(0))) {
            return compileGroup(data, criteriaJson);
        }

        if (isUnary(criteriaJson)) {
            return compileUnary(data, criteriaJson);
        }

        return compileBinary(data, criteriaJson);
    }

    private static Boolean compileGroup(Map<String, Object> data, List<Object> criteriaJson) {
        List<Boolean> operands = new ArrayList<>();
        var isAnd = true;
        var nextIsAnd = true;
        for (var item : criteriaJson) {
            if (isCriteria(item)) {
                if (operands.size() > 1 && isAnd != nextIsAnd) {
                    throw new RuntimeException("Mixing of and/or is not allowed inside a single group");
                }

                isAnd = nextIsAnd;
                operands.add(compileCore(data, (List<Object>) item));
                nextIsAnd = true;
            } else {
                nextIsAnd = Objects.equals(item, "and") || Objects.equals(item, "&");
            }
        }
        Boolean result = null;
        for (var operand : operands) {
            if (result == null)
                result = operand;
            else if (isAnd) {
                result = result && operand;
            } else {
                result = result || operand;
            }
        }
        return result;
    }

    private static Boolean compileUnary(Map<String, Object> data, List<Object> criteriaJson) {
        return !(compileCore(data, (List<Object>) criteriaJson.get(1)));
    }

    private static Boolean compileBinary(Map<String, Object> data, List<Object> criteriaJson) {
        var hasExplicitOperation = criteriaJson.size() > 2;

        String clientAccessor = (String) criteriaJson.get(0);
        var clientOperation = hasExplicitOperation ? (String) criteriaJson.get(1) : "=";
        String clientValue = String.valueOf(criteriaJson.get(hasExplicitOperation ? 2 : 1));
        return getPredicate(data, clientAccessor, clientOperation, clientValue);
    }

    private static Boolean isCriteria(Object item) {
        return item instanceof List;
    }

    private static Boolean isUnary(List<?> criteriaJson) {
        return Objects.equals(criteriaJson.get(0), "!");
    }

    private static Boolean getPredicate(Map<String, Object> data, String clientAccessor, String clientOperation, String clientValue) {
        Object value = data.get(clientAccessor);
        if (value == null) {
            return false;
        }
        Comparable comparableValue = null;
        Comparable comparableData = null;

        String type = value.getClass().getSimpleName();
        switch (type) {
            case "String" -> {
                if (value.equals("null")) {
                    return false;
                }
                comparableValue = clientValue.trim();
                comparableData = String.valueOf(value).trim();
            }
            case "Date" -> {
                comparableValue = Long.parseLong(clientValue);
                comparableData = Long.parseLong((String) value);
            }
            case "Boolean", "boolean" -> {
                comparableValue = Boolean.parseBoolean(clientValue);
                comparableData = Boolean.parseBoolean((String) value);
            }
            case "Integer", "Double", "Long", "int", "BigInteger" -> {
                comparableValue = Double.parseDouble(clientValue);
                comparableData = Double.parseDouble(value.toString());
            }
        }

        if (comparableValue == null) {
            throw new RuntimeException("Not found comparable");
        }

        switch (clientOperation) {
            case ">" -> {
                return ComparableUtils.is(comparableData).greaterThan(comparableValue);
            }
            case ">=" -> {
                return ComparableUtils.is(comparableData).greaterThanOrEqualTo(comparableValue);
            }
            case "<" -> {
                return ComparableUtils.is(comparableData).lessThan(comparableValue);
            }
            case "<=" -> {
                return ComparableUtils.is(comparableData).lessThanOrEqualTo(comparableValue);
            }
            case "=" -> {
                if (type.equals("String")) {
                    return StringUtils.equals(comparableData.toString(), comparableValue.toString());
                }
                return comparableData.compareTo(comparableValue) == 0;
//                return ComparableUtils.is(comparableData).equals(comparableValue);
            }
            case "<>" -> {
                if (type.equals("String")) {
                    return !StringUtils.equals(comparableValue.toString(), comparableValue.toString());
                }
                return comparableData.compareTo(comparableValue) != 0;
//                return !ComparableUtils.is(comparableData).equals(comparableData);
            }
            case "contains" -> {
                return StringUtils.contains(comparableData.toString(), comparableValue.toString());
            }
            case "notcontains" -> {
                return !StringUtils.contains(comparableData.toString(), comparableValue.toString());
            }
            case "startswith" -> {
                return StringUtils.startsWith(comparableData.toString(), comparableValue.toString());
            }
            case "endswith" -> {
                return StringUtils.endsWith(comparableData.toString(), comparableValue.toString());
            }
        }

        throw new RuntimeException("Not found state");
    }
}
