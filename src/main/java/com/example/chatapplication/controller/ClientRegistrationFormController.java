package com.example.chatapplication.controller;

import com.example.chatapplication.client.Client;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class ClientRegistrationFormController {

    @FXML
    private JFXButton btnRegister;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField txtName;

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        load();
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
//        load();
    }

    private void load() throws IOException {
        if (Pattern.matches("^[a-zA-Z\\s]+", txtName.getText())) {
            Client client = new Client(txtName.getText());
            Thread thread = new Thread(client);
            thread.start();
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }
}
