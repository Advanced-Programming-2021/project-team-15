package sample.controller;

import org.json.JSONObject;
import sample.controller.menuController.LoginController;
import sample.controller.menuController.MenuController;
import sample.controller.menuController.ShopController;
import sample.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class ServerController {
    private static ServerSocket serverSocket;
    private static Socket socket;

    public static void run() {
        System.out.println("running...");
        try {
            System.out.println("Server starting...");
            serverSocket = new ServerSocket(8000);
            while (true) {
                socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> {
                    try {
                        String command = dataInputStream.readUTF();
                        while (!command.equals("terminate")) {
                            dataOutputStream.writeUTF(processString(command));
                            dataOutputStream.flush();
                            command = dataInputStream.readUTF();
                        }
                        serverSocket.close();
                        socket.close();
                        dataInputStream.close();

                    } catch (IOException e) {
                        if (e instanceof SocketException)
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
            System.out.println(LoginController.class.getName());
            Class<?> clazz = Class.forName("sample.controller.menuController."+jsonObj.get("class"));
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getDeclaredMethod("callMethods",JSONObject.class);
            return (String) method.invoke(obj,jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
