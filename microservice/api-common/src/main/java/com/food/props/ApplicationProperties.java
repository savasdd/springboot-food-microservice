package com.food.props;

import java.util.Properties;

public class ApplicationProperties {

    public static String getValue(String key) {
        return getProp().getProperty(key);
    }

    static Properties prop;
    static Properties getProp() {
        if (prop == null) {
            prop = new BaseProperties("application.properties").getProperties();
        }
        return prop;
    }
}
