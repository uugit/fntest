package util;

import java.io.FileInputStream;
import java.util.Properties;

public class Conf {

    private Conf() {
    }

    private static final Conf instance = new Conf();

    public static void init(String fileName) {
        FileInputStream inStream;
        try {
            inStream = new FileInputStream(fileName);
            instance.properties.load(inStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("load conf failed:" + fileName);
        }
    }

    public static Conf getInstance() {
        return instance;
    }

    private Properties properties = new Properties();

    public String getProperty(String key) {
        /*
        if (properties.isEmpty())
        	throw new RuntimeException("conf has not be init");
        */
        return properties.getProperty(key);
    }
}
