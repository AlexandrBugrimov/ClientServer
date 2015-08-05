package ru.bugrimov.application;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class Server {
    public static String EXIT = "exit";
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public void start() throws IOException {
        try {
            PropertiesServer propertiesServer = new PropertiesServer();
            PropertyFile propertyFile = new PropertyFile();
            propertyFile.recordPropertyFile(propertiesServer.initializeProperties());

            String stringServer = propertiesServer.getLocalhost() + ":" + propertiesServer.getPort();
            System.out.println(" Сервер - " + stringServer + " - запущен");

            serverSocket = new ServerSocket(propertiesServer.getPort());
            clientSocket = serverSocket.accept();

            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String inputMessage = "";
            String outputMessage;

            while (!inputMessage.equals(EXIT)) {
                inputMessage = inputStream.readUTF();
                System.out.print(" Запрос клиента: " + inputMessage + "\n");
                if (!inputMessage.equals(EXIT)) {
                    outputMessage = reversString(inputMessage);
                } else {
                    outputMessage = " КЛИЕНТ отключен";
                }
                outputStream.writeUTF(outputMessage);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            serverSocket.close();
            clientSocket.close();
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
