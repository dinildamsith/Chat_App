package com.example.chatapplication.controller;

import com.example.chatapplication.server.Server;
import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    public JFXButton btnStart;
    public AnchorPane pane;
    public ImageView rocketImage;

    public void btnStartOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        startServer();

        pane.getChildren().clear();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ChatForm.fxml"))));
        stage.show();
    }

    private void startServer() throws IOException {
        Server server = Server.getServerSocket();
        Thread thread = new Thread(server);
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(600), rocketImage);
        zoomIn.setFromX(0.5);
        zoomIn.setFromY(0.5);
        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);
        zoomIn.play();
    }
}
