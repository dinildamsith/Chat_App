package com.example.chatapplication.controller;

import com.example.chatapplication.server.ClientHandler;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatFormController implements Initializable {


    private final String[] emojis = {
            "\uD83D\uDE00", // 😀
            "\uD83D\uDE01", // 😁
            "\uD83D\uDE02", // 😂
            "\uD83D\uDE03", // 🤣
            "\uD83D\uDE04", // 😄
            "\uD83D\uDE05", // 😅
            "\uD83D\uDE06", // 😆
            "\uD83D\uDE07", // 😇
            "\uD83D\uDE08", // 😈
            "\uD83D\uDE09", // 😉
            "\uD83D\uDE0A", // 😊
            "\uD83D\uDE0B", // 😋
            "\uD83D\uDE0C", // 😌
            "\uD83D\uDE0D", // 😍
            "\uD83D\uDE0E", // 😎
            "\uD83D\uDE0F", // 😏
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE11", // 😑
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE13"  // 😓
    };
    public AnchorPane emojiAnchorpane;
    public GridPane emojiGridpane;
    @FXML
    private JFXButton btnSend;
    @FXML
    private TextField txtMessage;
    @FXML
    private VBox vBox;

    @FXML
    void btnAddClientOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClientRegistrationForm.fxml"))));
        stage.setTitle("Add New Client");
        stage.setAlwaysOnTop(true);
        stage.showAndWait();
    }

    @FXML
    void btnSendOnAction() throws IOException {
        emojiAnchorpane.setVisible(false);
        String text = txtMessage.getText();
        if (!Objects.equals(text, "")) {
            sendMessage(text);
        } else {
            ButtonType ok = new ButtonType("Ok");
            ButtonType cancel = new ButtonType("Cancel");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Empty message. Is it ok?", ok, cancel);
            alert.showAndWait();
            ButtonType result = alert.getResult();
            if (result.equals(ok)) {
                sendMessage(text);
            }
        }
    }

    private void sendMessage(String text) throws IOException {
        ClientHandler.broadcast(text);
        HBox hBox = new HBox();
        hBox.setStyle("-fx-alignment: center-right;-fx-fill-height: true;-fx-min-height: 50;-fx-pref-width: 520;-fx-max-width: 520;-fx-padding: 10");
        Label messageLbl = new Label(text);
        messageLbl.setStyle("-fx-background-color:  #2980b9;-fx-background-radius:15;-fx-font-size: 18;-fx-font-weight: normal;-fx-text-fill: white;-fx-wrap-text: true;-fx-alignment: center-left;-fx-content-display: left;-fx-padding: 10;-fx-max-width: 350;");
        hBox.getChildren().add(messageLbl);
        vBox.getChildren().add(hBox);
        txtMessage.clear();
    }

    public void txtMessageOnAction(ActionEvent actionEvent) throws IOException {
        btnSendOnAction();
    }

    public void btnEmojiOnAction() {
        emojiAnchorpane.setVisible(!emojiAnchorpane.isVisible());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emojiAnchorpane.setVisible(false);
        int buttonIndex = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (buttonIndex < emojis.length) {
                    String emoji = emojis[buttonIndex];
                    JFXButton emojiButton = createEmojiButton(emoji);
                    emojiGridpane.add(emojiButton, column, row);
                    buttonIndex++;
                } else {
                    break;
                }
            }
        }
    }

    private JFXButton createEmojiButton(String emoji) {
        JFXButton button = new JFXButton(emoji);
        button.getStyleClass().add("emoji-button");
        button.setOnAction(this::emojiButtonAction);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        button.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-background-color: #F0F0F0; -fx-border-radius: 50");
        return button;
    }

    private void emojiButtonAction(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        txtMessage.appendText(button.getText());
    }

    private String getRandomColorStyle() {
        // Generate a random color in hexadecimal format
        String[] colorCodes = {"#27ae60", "#f39c12", "#2980b9", "#8e44ad", "#e74c3c", "#2c3e50", "#1abc9c", "#d35400", "#c0392b"};
        int randomIndex = (int) (Math.random() * colorCodes.length);
        return colorCodes[randomIndex];
    }
}

