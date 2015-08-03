package ru.bugrimov.client_server;

import ru.bugrimov.client_server.config.Configuration;
import ru.bugrimov.client_server.config.FileOfConfiguration;

import static ru.bugrimov.client_server.config.FileOfConfiguration.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {
    protected static ServerSocket server;
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static Socket client;

    public static void main(String[] args) {
        try {
            Configuration configuration = new Configuration();      // Создаем конфигурацию
            new FileOfConfiguration(configuration, CONFIG_INI);     // Записываем конфишурации в файл *.ini

            final String HOST = configuration.getLocalhost();
            final int PORT = configuration.getPort();
            server = new ServerSocket(PORT);

            System.out.println(" Сервер <" + HOST + ": " + PORT + "> - запущен.");


            for (;;) {
                client = server.accept();
                writer = new PrintWriter(client.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String str = reader.readLine();
                if (str != null) {
                    System.out.println(" Запрос клиента -> " + str);
                    writer.write(reversString(str) + "\n");
                    writer.flush();
                }
            }
        } catch (IOException | NullPointerException e) {
            System.err.println(" Ошибка: " + e.getMessage());
        } finally {
            if (server != null || client != null || writer != null || reader != null) {
                try {
                    server.close();
                    writer.close();
                    client.close();
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static String reversString(final String string) {
        String newString = "";
        StringTokenizer stringTokenizer = new StringTokenizer(string, " ,.!?:;-\n\t", true);

        while (stringTokenizer.hasMoreTokens()) {
            StringBuilder stringBuilder = new StringBuilder(stringTokenizer.nextToken());
            newString += stringBuilder.reverse();
        }
        return newString;
    }
}

