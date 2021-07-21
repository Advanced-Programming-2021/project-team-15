package sample.controller;

import org.json.JSONObject;
import sample.controller.menuController.LoginController;
import sample.controller.menuController.MenuController;
import sample.controller.menuController.ShopController;
import sample.model.User;

import java.io.*;
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
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                new Thread(() -> {
                    try { Object command = objectInputStream.readObject();
                        while (!( command instanceof String && command.equals("terminate"))) {
                            if(!command.equals("")) {
                            objectOutputStream.writeObject(processString(command));
                            objectOutputStream.flush();}
                            command = objectInputStream.readObject();
                        }
                        serverSocket.close();
                        socket.close();
                        objectInputStream.close();

                    } catch (IOException | ClassNotFoundException e) {
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

    private static Object processString(Object command) {
       HashMap<String, Object> jsonObj = (HashMap<String, Object>) command;
        try {
            System.out.println(LoginController.class.getName());
            Class<?> clazz = Class.forName("sample.controller.menuController."+jsonObj.get("class"));
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getDeclaredMethod("callMethods",HashMap.class);

            return  method.invoke(obj,jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("hi bitch");
            return null;
        }
    }

}
