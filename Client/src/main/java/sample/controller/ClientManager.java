package sample.controller;

import java.io.*;
import java.net.Socket;

public class ClientManager {
    private static ObjectInputStream objectInputStream;
    private static  ObjectOutputStream objectOutputStream;
    public static void run() {
        try {
            Socket socket = new Socket("localHost", 8000);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("khar");
           objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(Object input) {
        try {
            objectOutputStream.writeObject(input);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object sendAndGetResponse(Object input) {
        try {
            send(input);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
