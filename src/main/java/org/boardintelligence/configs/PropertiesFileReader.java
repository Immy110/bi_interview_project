package org.boardintelligence.configs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesFileReader {

    private final Properties properties;
    private static PropertiesFileReader propertiesFileReader;


    public PropertiesFileReader() {
        BufferedReader reader;
        BufferedReader envReader = null;
        String propertyFileName = "application.properties";
        String envPropertyFileName = "application-%s.properties";
        String environmentName = System.getProperty("environment");
        envPropertyFileName = String.format(envPropertyFileName, environmentName);

        Path propertyFile = Paths.get(".", "src", "main", "resources", "configs", propertyFileName);
        Path envPropertyFile = Paths.get(".", "src", "main", "resources", "configs", envPropertyFileName);

        try {
            reader = new BufferedReader(new FileReader(propertyFile.toString()));
            if (environmentName != null)
                envReader = new BufferedReader(new FileReader(envPropertyFile.toString()));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
                if (null != envReader) {
                    properties.load(envReader);
                    envReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Application properties not found : " + e.getMessage());
        }
    }

    public static PropertiesFileReader getInstance() {
        if (propertiesFileReader == null) {
            propertiesFileReader = new PropertiesFileReader();
        }
        return propertiesFileReader;
    }

    public String getConfigurationProperties(String configName) {
        String configValue = properties.getProperty(configName);
        if (configValue != null) return configValue;
        else throw new RuntimeException("%s not specified in the application properties file.".formatted(configName));
    }

}
