package com.food.data.filter;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Slf4j
public class Filter<T> {

    private T instance;
    private ECondition condition;
    private EOperator operation;
    private ArrayList<Object> filter;
    private final KeyValue keyValue = new KeyValue();
    private boolean ifCond = true;

    private final Map<Integer, ArrayList<Object>> filterList = new HashMap<>();
    private ArrayList<Object> currentFilter;
    private ECondition groupCondition;

    public Filter(Class<T> clazz) {
        try {
            instance = (T) Builder.build(clazz.getDeclaredConstructor().newInstance()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Filter<T> with(Consumer<T> setter) {
        T beforeInstance = null;
        try {
            beforeInstance = (T) Builder.build(this.instance.getClass().getDeclaredConstructor().newInstance()).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ifCond) {
            setter.accept(instance);
            DiffNode diff = ObjectDifferBuilder.buildDefault().compare(beforeInstance, instance);

            if (diff.hasChanges()) {
                diff.visit((node, visit) -> {
                    if (!node.hasChildren()) {
                        String name = getDiffName(node, null);
                        if (name != null && node.canonicalGet(instance) != null) {
                            addFilter(name, node.canonicalGet(instance));
                        }
                    }
                });
            }
        }
        return this;
    }

    public Filter<T> custom(String key, Object value) {
        addFilter(key, value);
        return this;
    }

    public ArrayList<Object> get() {
        if (filter.size() > 1) {
            ArrayList<Object> newGroup = new ArrayList<>();
            newGroup.add(filter);
            filter = newGroup;
        }
        condition(ECondition.and);
        operation(EOperator.equal);
        addDelete();
        return filter;
    }

    public void addDelete() {
        T deleteInstance;
        try {
            deleteInstance = (T) Builder.build(this.instance.getClass().getDeclaredConstructor().newInstance()).get();

            DiffNode diff = ObjectDifferBuilder.buildDefault().compare(deleteInstance, instance);

            if (diff.hasChanges()) {
                diff.visit((node, visit) -> {
                    if (node.getPropertyName() != null && node.getPropertyName().equalsIgnoreCase("isDeleted")) {
                        String name = getDiffName(node, null);
                        if (name != null && node.canonicalGet(deleteInstance) != null) {
                            addFilter(name, node.canonicalGet(deleteInstance));
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static <T> Filter<T> build(Class<T> clazz) {
        return new Filter<>(clazz);
    }

    public Filter<T> condition(ECondition condition) {
        if (filter == null) {
            filter = new ArrayList<>();
        }
        this.condition = condition;
        return this;
    }

    public Filter<T> operation(EOperator operation) {
        if (filter == null) {
            filter = new ArrayList<>();
        }
        this.operation = operation;
        return this;
    }

    public Filter<T> If(BooleanSupplier condition) {
        this.ifCond = condition.getAsBoolean();
        return this;
    }

    public Filter<T> endIf() {
        this.ifCond = true;
        return this;
    }

    public Filter<T> startGroup() {
        ArrayList<Object> newGroup = new ArrayList<>();
        filterList.put(filterList.size(), newGroup);
        currentFilter = newGroup;
        this.groupCondition = this.condition;
        return this;
    }

    public Filter<T> endGroup() {
        if (currentFilter.size() > 0) {
            if (this.filter.size() > 0) {
                this.filter.add(getConditionString(this.groupCondition != null ? this.groupCondition : this.condition));
            }
            this.filter.add(currentFilter.clone());
            int key = filterList.entrySet().stream().max((entry1, entry2) -> entry1.getKey() > entry2.getKey() ? 1 : -1).get().getKey();
            filterList.remove(key);
            if (filterList.size() > 0) {
                currentFilter = filterList.entrySet().stream().max((entry1, entry2) -> entry1.getKey() > entry2.getKey() ? 1 : -1).get().getValue();
            } else {
                currentFilter = null;
            }
        } else {
            currentFilter = null;
        }
        return this;
    }


    private void addFilter(String key, Object value) {
        ArrayList<Object> processFilter = currentFilter != null ? currentFilter : this.filter;
        if (processFilter.size() > 0) {
            processFilter.add(getConditionString(this.condition));
        }
        ArrayList<Object> tempFilter = new ArrayList<>();
        tempFilter.add(key);
        tempFilter.add(getOperatorString(this.operation));
        tempFilter.add(value);
        processFilter.add(tempFilter);
        clearValue();
    }

    private void clearValue() {
        try {
            instance = (T) Builder.build(this.instance.getClass().getDeclaredConstructor().newInstance()).get();
//            instance = (T) setChildren(this.instance.getClass().getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private KeyValue getSuperClassKeyValue(Object object) {
        for (Field item : object.getClass().getSuperclass().getDeclaredFields()) {
            String fieldName = item.getName();
            if (!fieldName.equals("id")) continue;
            Object itemValue;
            item.setAccessible(true);
            try {
                itemValue = item.get(object);
                if (itemValue != null) {
                    keyValue.setKey(fieldName);
                    keyValue.setValue(itemValue);
                    return keyValue;
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private String getOperatorString(EOperator operator) {
        switch (operator) {
            case equal:
                return "=";
            case greater:
                return ">";
            case greaterEqual:
                return ">=";
            case less:
                return "<";
            case lessEqual:
                return "<=";
            case notEqual:
                return "<>";
            case startswith:
                return "startswith";
        }

        return null;
    }

    private String getConditionString(ECondition condition) {
        switch (condition) {
            case and:
                return "and";
            case or:
                return "or";
        }

        return null;
    }

    private String getDiffName(DiffNode diff, String name) {
        if (diff.getPropertyName() != null) {
            if (name == null) {
                name = diff.getPropertyName();
            } else {
                name = diff.getPropertyName() + "." + name;
            }
        }
        if (diff.getParentNode() != null) {
            return getDiffName(diff.getParentNode(), name);
        }
        return name;
    }

    @Getter
    @Setter
    static class KeyValue {
        private String key;
        private Object value;
    }
}
