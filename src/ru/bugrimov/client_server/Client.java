package ru.bugrimov.client_server;

import ru.bugrimov.client_server.config.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static ru.bugrimov.client_server.config.FileOfConfiguration.*;

public class Client {

    protected Socket client;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {
        new Client();
    }

    Client() {
        while (true) {
            if (connectToServer()) {
                System.out.print("Введите строку: ");

                writer.write(inputString() + "\n");
                writer.flush();

                String str;
                try {
                    while ((str = reader.readLine()) != null) {
                        System.out.println("Получено от сервера: " + str);
                    }
                    writer.close();
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }

    private boolean connectToServer() {
        try {
            Configuration configuration = readFileConfiguration(PATH + CONFIG_INI);     // Считываем конфигурацию из файла
            final String HOST = configuration.getLocalhost();
            final int PORT = configuration.getPort();

            client = new Socket(HOST, PORT);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream());
            System.out.println(" * Клиент запущен * ");
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            return false;
        }
    }

    private String inputString() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}

