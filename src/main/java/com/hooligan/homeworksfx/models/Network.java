package com.hooligan.homeworksfx.models;

import com.hooligan.homeworksfx.controllers.ChatController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    private static final String AUTH_CMD_PREFIX = "/auth"; // + login + password
    private static final String AUTHOK_CMD_PREFIX = "/authok"; // + username
    private static final String AUTHERR_CMD_PREFIX = "/autherr"; // + error message
    private static final String CLIENT_MSG_CMD_PREFIX = "/cmsg"; // + msg
    private static final String SERVER_MSG_CMD_PREFIX = "/smsg"; // + server msg
    private static final String PRIVATE_MSG_CMD_PREFIX = "/pmsg"; // + private msg
    private static final String STOP_SERVER_CMD_PREFIX = "/stop";
    private static final String END_CLIENT_CMD_PREFIX = "/end";

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8186;
    private DataInputStream in;
    private DataOutputStream out;
    private final String host;
    private final int port;

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network() {
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
    }

    public void connect() {

        try {
            Socket socket = new Socket(host, port);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection didn't establish");
        }
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The message didn't send");
        }
    }

    public void waitMessage(ChatController chatController) {
        Thread t = new Thread(() -> {
        try {
            while (true) {

                String message = in.readUTF();
                chatController.appendMessage("I: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        });

        t.isDaemon();
        t.start();
    }

}

