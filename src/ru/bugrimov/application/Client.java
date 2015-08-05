package ru.bugrimov.application;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket clientSocket;

    public void start() {
        try {
            PropertyFile propertyFile = new PropertyFile();
            PropertiesServer propertiesServer = propertyFile.readPropertyFile("config.properties");
            if (connectToServer(propertiesServer)) {
                System.out.println(" Клиент подключен к серверу - " + propertiesServer.getLocalhost() + ":" + propertiesServer.getPort());
                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                String inputMessage;
                String outputMessage = "";

                while (!outputMessage.equals(Server.EXIT)) {
                    System.out.print(" Запрос клиента: ");
                    outputMessage = bufferedReader.readLine();
                    outputStream.writeUTF(outputMessage);
                    inputMessage = inputStream.readUTF();
                    System.out.println(" Ответ сервера: " + inputMessage);
                }
                inputStream.close();
                outputStream.close();
                bufferedReader.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private boolean connectToServer(PropertiesServer propertiesServer) {
        try {
            clientSocket = new Socket(propertiesServer.getLocalhost(), propertiesServer.getPort());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
