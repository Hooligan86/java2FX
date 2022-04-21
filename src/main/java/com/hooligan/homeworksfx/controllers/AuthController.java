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
    private TextField loginReg;
    @FXML
    private PasswordField passwordReg;
    @FXML
    private TextField usernameReg;

    public void checkAuth(String login, String password) {

        if (login == null || password == null) {
            startClient.showErrorAlert("Error input authentication", "The fields empty");

        }

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

    @FXML
    public void checkAuth() {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        checkAuth(login, password);
    }

    @FXML
    void signUp() {
        String login = loginReg.getText().trim();
        String password = passwordReg.getText().trim();
        String username = usernameReg.getText().trim();

        if (login.length() == 0 || password.length() == 0 || username.length() == 0) {
            startClient.showErrorAlert("Error sign up", "Fields can't be empty");
            return;
        }

        String signUpErrorMessage = network.sendSignUpMessage(login, password, username);

        if (signUpErrorMessage == null) {
//            startClient.showInformationAlert("Регистрация успешно пройдена", "Можете перейти к аутентификации");
            startClient.showInformationAlert("You are signed up!", "Welcome");
            checkAuth(login, password);
        } else {
            startClient.showErrorAlert("Error sign up", signUpErrorMessage);
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setStartClient(StartClient startClient) {
        this.startClient = startClient;
    }
}
