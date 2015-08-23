package ru.bugrimov.application;

import java.io.*;
import java.util.Properties;

public class PropertyFile {

    private static final String FILE_NAME = "config.properties";
    private static final String DIRECTORY_RESOURCES = ".\\src\\ru\\bugrimov\\resources\\";
    private static final String HOST_KEY = "server.host";
    private static final String PORT_KEY = "server.port";

    public PropertiesServer readPropertyFile(final String fileName) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(DIRECTORY_RESOURCES + fileName));
        return new PropertiesServer(properties.getProperty(HOST_KEY), Integer.parseInt(properties.getProperty(PORT_KEY)));
    }

    public void recordPropertyFile(final Properties properties) throws IOException {
        properties.store(new FileOutputStream(DIRECTORY_RESOURCES + FILE_NAME), null);
    }
}
