package ru.bugrimov.client_server.config;

import java.io.IOException;
import java.net.ServerSocket;

/** Конфигурация */
public class Configuration {
    private static final String HOST_NAME = "localhost";
    private String localhost;
    private int port;

    public Configuration() {
        this.localhost = HOST_NAME;
        this.port = findFreePort();
    }

    public Configuration(final String localhost, final int port) {
        this.localhost = localhost;
        this.port = port;
    }

    public String getLocalhost() {
        return localhost;
    }

    public int getPort() {
        return port;
    }

    private int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);

            return socket.getLocalPort();
        } catch (IOException e) {
            throw new IllegalStateException();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ignored) { }
            }
        }
    }
}
