package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static PropertyManager instance;
    private Properties properties;
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyManager.class);
    private static final String CONFIGURATION_FILE_NAME = "config.xml";

    private PropertyManager() {
        getProperties();
    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    private void getProperties() {
        properties = loadProperties();
    }

    private Properties loadProperties() {
        Properties properties = null;
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIGURATION_FILE_NAME);
            if (inputStream == null) {
                LOGGER.error("The path of the properties file is not correct");
                System.exit(1);
            }
            properties = new Properties();
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            LOGGER.error("Error occurred when reading from the input stream from the file " + CONFIGURATION_FILE_NAME + "\n" + e.getMessage());
            System.exit(1);
        }
        return properties;
    }

    public String getProperty(String property) {
        if (properties.getProperty(property) == null)
            return "";
        else
            return properties.getProperty(property);
    }
}

