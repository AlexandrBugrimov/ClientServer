package ru.bugrimov.application;

import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;

public class PropertiesServer {
    private static final String HOST_NAME = "localhost";
    private static final int DEFAULT_PORT = 55_556;
    private static final String HOST_KEY = "server.host";
    private static final String PORT_KEY = "server.port";

    private String localhost;
    private int port;

    PropertiesServer() {
        this(HOST_NAME);
    }

    PropertiesServer(final String localhost) {
        this.localhost = localhost;
        this.port = searchFreePort();
    }

    PropertiesServer(final String localhost, final int port) {
        this.localhost = localhost;
        this.port = port;
    }

    public String getLocalhost() {
        return localhost;
    }

    public int getPort() {
        return port;
    }

    private int searchFreePort() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            serverSocket.setReuseAddress(true);
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return DEFAULT_PORT;
    }

    public Properties initializeProperties() {
        Properties properties = new Properties();
        properties.setProperty(HOST_KEY, getLocalhost());
        properties.setProperty(PORT_KEY, String.valueOf(getPort()));
        return properties;
    }


}
