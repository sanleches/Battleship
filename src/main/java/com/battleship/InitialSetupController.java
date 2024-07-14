package com.battleship;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class InitialSetupController {

    private final String imagePath = "file:src/main/java/com/battleship/images/";

    @FXML
    private VBox initialSetupRoot;

    @FXML
    private TextField usernameField;

    @FXML
    private Button onlineButton;

    @FXML
    private Button offlineButton;

    @FXML
    private void initialize() {

        // Add the logo image on top
        Image logoImage = new Image(imagePath + "logo.png");
        ImageView titleBanner = new ImageView(logoImage);
        titleBanner.setFitWidth(350);  // Adjust width as needed
        //titleBanner.setFitHeight(200);
        titleBanner.setPreserveRatio(true);
        initialSetupRoot.getChildren().add(0, titleBanner);  // Add at the top of the VBox
        initialSetupRoot.setSpacing(40);  // Adjust spacing as needed


        // Initialize and style the username text field
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-background-color: #3B6491; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");


        // Style the buttons
        styleButton(onlineButton, "#3B6491");
        styleButton(offlineButton, "#3B6491");

        // Button handlers for initial setup
        onlineButton.setOnAction(event -> {
            String username = usernameField.getText();
            App.switchToMainScene(username, 1);
        });

        offlineButton.setOnAction(event -> {
            String username = usernameField.getText();
            App.switchToMainScene(username, 0);
        });
    }

    private void styleButton(Button button, String color) {
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 20px;");
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: " + lightenColor(color, 0.2) + "; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 20px;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 20px;"));
    }

    private String lightenColor(String color, double factor) {
        javafx.scene.paint.Color c = javafx.scene.paint.Color.web(color);
        double red = Math.min(c.getRed() + factor, 1.0);
        double green = Math.min(c.getGreen() + factor, 1.0);
        double blue = Math.min(c.getBlue() + factor, 1.0);
        return String.format("#%02X%02X%02X",
                (int) (red * 255),
                (int) (green * 255),
                (int) (blue * 255));
    }
}
