package com.food.spesification;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

@Slf4j
public class Builder<T> {

    private final int maxRecursiveLevel = 5;
    private final T instance;
    private final boolean hasDelete;

    public Builder(Object object, boolean hasDelete) throws Exception {
        this.hasDelete = hasDelete;
        instance = (T) recursiveFields(object, 0, false, true);
    }


    public T get() {
        return instance;
    }

    public static <T> Builder<T> build(Object object, boolean hasDelete) {
        try {
            return new Builder<>(object, hasDelete);
        } catch (Exception e) {
            return null;
        }
    }

    private Object recursiveFields(Object instance, int level, boolean isdel, boolean parentIsdel) throws Exception {
        if (level > maxRecursiveLevel) {
            return instance;
        }
        //TODO is_delete
        //if (hasDelete && isdel && parentIsdel && instance instanceof BaseEntity) {
        //    ((BaseEntity) instance).setIsDeleted(0L);
        //}

        Field[] fields = instance.getClass().getDeclaredFields();

        if (fields.length > 0) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(ManyToOne.class)
                        || field.isAnnotationPresent(OneToOne.class)
                        || field.isAnnotationPresent(ManyToMany.class)
                        || field.isAnnotationPresent(OneToMany.class)
                ) {


                    if (field.getGenericType() instanceof ParameterizedType) {
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
        }

        return instance;
    }
}
