package sample.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientManager {
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static ObjectInputStream objectInputStream;

    public static void run() {
        try {
            Socket socket = new Socket("localHost", 8000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("khar");
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(String input) {
        try {
            dataOutputStream.writeUTF(input);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendAndGetResponse(String input) {
        try {
            send(input);
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
