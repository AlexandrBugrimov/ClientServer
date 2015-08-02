package ru.bugrimov.client_server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/** Файл конфигурации */
public class FileOfConfiguration {
    public static final String CONFIG_INI = "config.ini";
    public static final String PATH = ".\\resource\\";
    private static final String HOST = "host:";
    private static final String PORT = "port:";

    private String localhostStr = "";
    private String portStr = "";

    public FileOfConfiguration(final Configuration configuration, final String fileName) {
        this.localhostStr += (HOST + " " + configuration.getLocalhost());
        this.portStr += (PORT + " " + configuration.getPort());
        this.fileWrite(fileName);
    }

    private String getPortStr() {
        return portStr;
    }

    private String getLocalhostStr() {
        return localhostStr;
    }

    private void fileWrite(final String fileName) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(PATH + fileName, false);
            fileWriter.write(getLocalhostStr());
            fileWriter.append('\n');
            fileWriter.write(getPortStr());
            fileWriter.flush();
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static Configuration readFileConfiguration(final String fileName) {
        FileReader fileReader = null;
        String localhost = null;
        int port = 0;
        try {
            fileReader = new FileReader(fileName);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                switch (scanner.next()) {
                    case HOST: localhost = scanner.next();   break;
                    case PORT: port = Integer.parseInt(scanner.next()); break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return new Configuration(localhost, port);
    }
}
