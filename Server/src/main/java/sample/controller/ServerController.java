package sample.controller;

import sample.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerController {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static HashMap<String, User> activeUsers = new HashMap<>();
    public static void run()
    {

        try {
            serverSocket = new ServerSocket(8000);
            while (true) {
                socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> {
                    try {
//                        while (true)
//                        {   String input = dataInputStream.readUTF();
////                            String result;
////                            dataOutputStream.writeUTF(result);
//                            dataOutputStream.flush();
//                        }
                        serverSocket.close();
                        socket.close();
                        dataInputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
