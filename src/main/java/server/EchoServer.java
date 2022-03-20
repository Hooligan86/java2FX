package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
    private static final int SERVER_PORT = 8186;
    private static DataOutputStream out;
    private static DataInputStream in;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                System.out.println("Waiting to connection");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection established");

                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
                try {
                    while (true) {
                        String message = in.readUTF();

                        if (message.equals("/server-stop")) {
                            System.out.println("Server stopped");
                            System.exit(0);
                        }

                        System.out.println("Client: " + message);
                        out.writeUTF(message);
                    }
                } catch (SocketException e) {
                    clientSocket.close();
                    System.out.println("Client disconnected");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();


        }
    }
}

