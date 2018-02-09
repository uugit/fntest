package config;

import java.lang.reflect.InvocationTargetException;

public interface IConfigLoader {

    GSRuntimeConfig loadConfig(String localConfigFilePath) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
