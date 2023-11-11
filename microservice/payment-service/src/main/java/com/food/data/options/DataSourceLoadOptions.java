package com.food.data.options;

import java.util.ArrayList;


public class DataSourceLoadOptions extends DataSourceLoadOptionsBase {
    public DataSourceLoadOptions() {
        setStringToLower(true);
    }

    private Object value;

    public Object getValue(String key) {
        getObjectValue(getFilter(), key);
        return value;
    }

    private void getObjectValue(Object object, String key) {
        for (Object obj : getFilter()) {
            if (obj instanceof ArrayList && ((ArrayList<?>) object).get(0) instanceof ArrayList && ((ArrayList<?>) object).get(2) instanceof ArrayList) {
                getObjectValue(obj, key);
            } else if (((ArrayList<?>) object).get(0).equals(key)) {
                this.value = ((ArrayList<?>) object).get(2);
                return;
            }
        }
    }
}
