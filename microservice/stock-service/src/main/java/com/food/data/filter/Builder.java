package com.food.data.filter;

import com.food.model.base.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Builder<T> {

    private final int maxRecursiveLevel = 5;
    private final T instance;
    private final boolean hasDelete;

    private final String fieldName;

    public Builder(Object object, boolean hasDelete) {
        this.hasDelete = hasDelete;
        this.fieldName = null;
        instance = (T) recursiveFields(object, 0, false, true);
    }

    public Builder(Object object) {
        this.hasDelete = false;
        this.fieldName = null;
        instance = (T) recursiveFields(object, 0, false, true);
    }

    public Builder(Object object, String fieldName) {
        this.hasDelete = false;
        this.fieldName = fieldName;
        instance = (T) recursiveFields(object, 0, false, true);
    }


    public T get() {
        return instance;
    }

    public static <T> Builder<T> build(Object object) {
        try {
            return new Builder<>(object);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> Builder<T> build(Object object, String fieldName) {
        try {
            return new Builder<>(object, fieldName);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> Builder<T> build(Object object, boolean hasDelete) {
        try {
            return new Builder<>(object, hasDelete);
        } catch (Exception e) {
            return null;
        }
    }

    private Object recursiveFields(Object instance, int level, boolean isdel, boolean parentIsdel) {
        if (level > maxRecursiveLevel) {
            return instance;
        }
        if (hasDelete && isdel && parentIsdel && instance instanceof BaseEntity) {
            ((BaseEntity) instance).setIsDeleted(0L);
        }

        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        if (declaredFields.length > 0) {
            fields.addAll(List.of(declaredFields));
        }
        Field[] declaredSuperclassFields = instance.getClass().getSuperclass().getDeclaredFields();
        if (declaredSuperclassFields.length > 0) {
            fields.addAll(List.of(declaredSuperclassFields));
        }

        for (Field field : fields) {
            if (field.isAnnotationPresent(ManyToOne.class)
                    || field.isAnnotationPresent(OneToOne.class)
                    || field.isAnnotationPresent(ManyToMany.class)
                    || field.isAnnotationPresent(OneToMany.class)
            ) {
                if (field.getGenericType() instanceof ParameterizedType) {
                    try {
                        field.setAccessible(true);
                        field.set(instance, recursiveFields(new ArrayList<>(), (level + 1), field.isAnnotationPresent(NotNull.class), (field.isAnnotationPresent(NotNull.class) && parentIsdel)));
                    } catch (Exception ignored) {
                    }
                    continue;
                }

                if (this.fieldName != null && !this.fieldName.equalsIgnoreCase(field.getName())) {
                    continue;
                }

                try {
                    Object child = Class.forName(field.getType().getName()).getDeclaredConstructor().newInstance();
                    field.setAccessible(true);
                    field.set(instance, recursiveFields(child, (level + 1), field.isAnnotationPresent(NotNull.class), (field.isAnnotationPresent(NotNull.class) && parentIsdel)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return instance;
    }
}
