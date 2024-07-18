package com.battleship.mainStageModules;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LogBox {
    private VBox logBox;
    private Label messageLabel;

    public LogBox() {
        initializeLogBox();
    }

    private void initializeLogBox() {
        logBox = new VBox();
        logBox.setStyle("-fx-background-color: #3B6491; -fx-padding: 10px;");
        logBox.setPrefHeight(640);
        logBox.setAlignment(Pos.CENTER);

        messageLabel = new Label("Welcome to Battleship!");
        messageLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        logBox.getChildren().add(messageLabel);
    }

    public VBox getLogBox() {
        return logBox;
    }

    public void updateMessage(String message) {
        messageLabel.setText(message);
    }
}
