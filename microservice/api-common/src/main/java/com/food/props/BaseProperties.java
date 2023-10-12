package com.food.props;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseProperties {
    public BaseProperties(String name) {
        prop= new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name);

        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            close(inputStream);
        }
    }
    public Properties prop;

    public Properties getProperties() {
        return prop;
    }

    private static void close(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
