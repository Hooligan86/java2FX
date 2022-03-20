package com.hooligan.homeworksfx.controllers;

import com.hooligan.homeworksfx.models.Network;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatController {


    @FXML
    private TextArea chatField;

    @FXML
    private ListView<String> users = new ListView<>();

    ObservableList<String> userList = FXCollections.observableArrayList("Alex","Lukas","David");

    @FXML
    private TextField textField;

    @FXML
    private Button sendButton;

    @FXML
    void clearChatField() {
        chatField.clear();
    }

    private Network network;

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void waitMessage(ChatController chatController){

    }

    @FXML
    void sendMessage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String message = chatField.getText().trim() + "\n" + "(" + simpleDateFormat.format(new Date()) + ") " + textField.getText();
        if(!textField.getText().isBlank()) {
            appendMessage(message);
//
            textField.clear();
        }

        network.sendMessage(message);
    }

    public void appendMessage(String message) {
        chatField.setText(message);
    }

    @FXML
    void initialize() {
        textField.setOnAction(event -> sendMessage());
        sendButton.setOnAction(event -> sendMessage());
        users.setItems(userList);
    }
}