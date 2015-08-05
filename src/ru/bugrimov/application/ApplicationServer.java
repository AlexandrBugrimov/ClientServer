package ru.bugrimov.application;

import java.io.IOException;

public class ApplicationServer {
    public static void main(String ... args) throws IOException {
        Server server = new Server();
        server.start();
    }
}
