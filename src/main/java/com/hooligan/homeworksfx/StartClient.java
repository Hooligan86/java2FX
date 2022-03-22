package com.hooligan.homeworksfx;

import com.hooligan.homeworksfx.controllers.ChatController;
import com.hooligan.homeworksfx.models.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartClient extends Application {
    private static Scanner sc = new Scanner(System.in);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartClient.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Chat!");
        stage.setScene(scene);
        stage.show();

//        Network network = new Network();
//        ChatController chatController = fxmlLoader.getController();
//
//        chatController.setNetwork(network);
//        network.connect();
//        network.waitMessage(chatController);

        Socket socket = new Socket("localhost", 8186);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        Thread write = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        out.writeUTF(sc.nextLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread read = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("Server: " + in.readUTF());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        read.start();
        write.start();


    }

    public static void main(String[] args) {
        launch();
    }
}