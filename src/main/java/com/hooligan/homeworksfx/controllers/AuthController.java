package com.hooligan.homeworksfx.controllers;

import com.hooligan.homeworksfx.StartClient;
import com.hooligan.homeworksfx.models.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    private Network network;
    private StartClient startClient;

    @FXML
    public void checkAuth() {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (login.length() == 0 || password.length() == 0) {
            startClient.showErrorAlert("Input error", "Fields can't be empty");
            return;
        }

        String authErrorMessage = network.sendAuthMessage(login, password);

        if (authErrorMessage == null) {
            startClient.openChatDialog();
        } else {
            startClient.showErrorAlert("Authentication error", authErrorMessage);
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setStartClient(StartClient startClient) {
        this.startClient = startClient;
    }
}
