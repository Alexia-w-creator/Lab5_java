package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Injector  {
    //private

    public <T> T inject(T Obj)
    {
        String fileProperties = "properties";
        try
        {
            Properties properties = new Properties();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream in = classloader.getResourceAsStream(fileProperties);
            properties.load(in);

            Class clas = Obj.getClass();
            Field[] fields = clas.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    field.setAccessible(true);
                    String pr = properties.getProperty(field.getType().getName());
                    Class<?> clazz = Class.forName(pr);
                    Object obj = clazz.getDeclaredConstructor().newInstance();
                    field.set(Obj,obj);
                }
            }
        }
        catch (IOException|ClassNotFoundException|NoSuchMethodException|
               IllegalAccessException|InstantiationException|InvocationTargetException err)
        {
            err.printStackTrace();
        }
        return Obj;
    }
}