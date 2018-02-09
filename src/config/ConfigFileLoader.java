package config;

import java.util.Properties;

public class ConfigFileLoader extends ConfigLoaderBase implements IConfigLoader {

    @Override
    public GSRuntimeConfig loadConfig(String localConfigFilePath) {

        Properties properties = super.loadBaseConfigProperties(localConfigFilePath);
        return super.loadConfigFromProperties(properties, null);
    }
}
