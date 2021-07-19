package sample.controller;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import sample.model.User;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
                    try {   String command = dataInputStream.readUTF();
                        while (!command.equals("terminate")) {
                            dataOutputStream.writeUTF(processString(command));
                            dataOutputStream.flush();
                            command = dataInputStream.readUTF();
                        }
                        serverSocket.close();
                        socket.close();
                        dataInputStream.close();

                    } catch (IOException e) {
                        if(e instanceof SocketException)
                            System.out.println("one client disconnected");
                        else e.printStackTrace();
                    }

                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processString(String command) {
        JSONObject jsonObj = new JSONObject(command);
        try {
            Class<?> clazz = Class.forName((String) jsonObj.get("class"));
            Method method = clazz.getDeclaredMethod("process");
            return (String) method.invoke(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
