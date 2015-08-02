package ru.bugrimov.client_server;

import ru.bugrimov.client_server.config.Configuration;

import java.net.Socket;

import static ru.bugrimov.client_server.config.FileOfConfiguration.*;

public class Client {

    private Socket client;

    public static void main(String[] args) {
        Configuration configuration = readFileConfiguration(PATH + CONFIG_INI);     // —читываем конфигурацию из файла
        final String HOST = configuration.getLocalhost();
        final int PORT = configuration.getPort();

        System.out.println(HOST);
        System.out.println(PORT);

    }
}

