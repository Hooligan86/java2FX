package com.hooligan.homeworksfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloController {


    @FXML
    private TextArea chatField;

    @FXML
    private ListView<String> users = new ListView<>();

    ObservableList<String> listItems = FXCollections.observableArrayList("Alex","Lukas","David");

    @FXML
    private Button sendButton;

    
    @FXML
    private MenuItem menuItemClear;

    @FXML
    private TextField textField;

    @FXML
    void clearChatField() {
        chatField.clear();
    }

    @FXML
    void sendMessage2(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            sendMessage();
        }
    }

    @FXML
    void sendMessage() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        if(!textField.getText().isEmpty()) {
            chatField.setText(chatField.getText().trim() + "\n" + "(" + simpleDateFormat.format(new Date()) + ") " + textField.getText());
            textField.clear();
        }
    }

    @FXML
    void initialize() {
        users.setItems(listItems);
    }
}