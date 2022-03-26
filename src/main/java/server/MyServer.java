package server;

import server.authentication.AuthenticationService;
import server.authentication.BaseAuthenticationService;
import server.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final ServerSocket serverSocket;
    private final AuthenticationService authenticationService;
    private final List<ClientHandler> clients;

    public MyServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        authenticationService = new BaseAuthenticationService();
        clients = new ArrayList<>();
    }

    public void start() {
        System.out.println("Server running");
        System.out.println("-------------------------------");

        try {
            while (true) {
                waitingAndProcessNewClientConnection();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitingAndProcessNewClientConnection() throws IOException {
        System.out.println("Waiting to client");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");

        processClientConnection(socket);
    }

    private void processClientConnection(Socket socket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, socket);
        clientHandler.handle();
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public synchronized void subscribe(ClientHandler handler) {
        clients.add(handler);
    }

    public synchronized void unSubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public synchronized boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client == sender) {
                continue;
            }
            client.sendMessage(sender.getUsername(), message);
        }
    }

    public void broadcastPrivateMessage(String message, ClientHandler sender) throws IOException {
        String[] parts = message.split(" ", 3);
        for (ClientHandler client : clients) {
            if(client == sender || !client.getUsername().equals(parts[1])){
                continue;
            }
            client.sendMessage(sender.getUsername(), parts[2]);
        }

    }
}
