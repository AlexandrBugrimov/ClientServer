package ru.bugrimov.application;

public class ApplicationClient {
    public static void main(String ... args) {
        try {
            Client client = new Client();
            client.start();
        } catch (NullPointerException e) {
            System.err.println(" Соединение с СЕРВЕРОМ не установлено ");
        }
    }
}
