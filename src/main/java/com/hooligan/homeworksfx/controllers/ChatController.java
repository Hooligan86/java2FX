package com.hooligan.homeworksfx.controllers;

import com.hooligan.homeworksfx.models.Network;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class ChatController {

    @FXML
    private TextArea chatField;
    @FXML
    private ListView<String> usersList = new ListView<>();
    @FXML
    private TextField textField;
    @FXML
    private Button sendButton;
    private String selectedRecipient;
    @FXML
    private Label usernameTitle;
    private Network network;

    @FXML
    void initialize() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/ServerChatHistory"))) {
            while (bufferedReader.ready()) {
                chatField.appendText( bufferedReader.readLine() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textField.setOnAction(event -> sendMessage());
        sendButton.setOnAction(event -> sendMessage());

        usersList.setCellFactory(lv -> {
            MultipleSelectionModel<String> selectionModel = usersList.getSelectionModel();
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                usersList.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (selectionModel.getSelectedIndices().contains(index)) {
                        selectionModel.clearSelection(index);
                        selectedRecipient = null;
                    } else {
                        selectionModel.select(index);
                        selectedRecipient = cell.getItem();
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    @FXML
    void clearChatField() {
        chatField.clear();
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void sendMessage() {
        String message = textField.getText().trim();
        textField.clear();

        if (message.trim().isEmpty()) {
            return;
        }

        if (selectedRecipient != null) {
            network.sendPrivateMessage(selectedRecipient, message);
        } else {
            network.sendMessage(message);
        }
//        network.sendMessage(message);
        appendMessage("I: " + message);
    }

    public void waitMessage(ChatController chatController) {

    }

    public void appendMessage(String message) {
        String timeStamp = DateFormat.getInstance().format(new Date());
        chatField.appendText(timeStamp + System.lineSeparator() + message + System.lineSeparator() + System.lineSeparator());
        chatHistory(timeStamp + System.lineSeparator() + message + System.lineSeparator());
    }

    private void chatHistory(String message) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/chatHistory", true))) {
            bufferedWriter.append(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void appendServerMessage(String serverMessage) {
        chatField.appendText(serverMessage);
        chatField.appendText(System.lineSeparator());
        chatField.appendText(System.lineSeparator());
    }

    public void setUsernameTitle(String username) {
        this.usernameTitle.setText(username);
    }

    public void updateUsersList(String[] users) {
        Arrays.sort(users);
        for (int i = 0; i < users.length; i++) {
            if (users[i].equals(network.getUsername())) {
                users[i] = ">>> " + users[i];
            }
        }

        usersList.getItems().clear();
        Collections.addAll(usersList.getItems(), users);
    }
}