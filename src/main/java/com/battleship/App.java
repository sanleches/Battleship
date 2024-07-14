package com.battleship;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    private static Scene initialSetupScene;
    private static Stage primaryStage;
    private static MainStage mainStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.primaryStage = primaryStage;

        // Initialize initial setup UI
        Parent initialSetupRoot = FXMLLoader.load(getClass().getResource("InitialSetup.fxml"));
        initialSetupScene = new Scene(initialSetupRoot, 600, 600);

        // Show initial setup scene
        primaryStage.setScene(initialSetupScene);
        primaryStage.setTitle("Initial Setup");
        primaryStage.show();
    }

    public static void switchToMainScene(String username, int mode) {
    if (mainStage == null) {
        mainStage = new MainStage();
    }
    mainStage.setUsername(username);

    // Center the stage on the screen
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    double centerX = screenBounds.getMinX() + screenBounds.getWidth() / 2;
    double centerY = screenBounds.getMinY() + screenBounds.getHeight() / 2;

    primaryStage.setScene(mainStage.getScene());
    primaryStage.setWidth(1920);  // Set window width to 1920
    primaryStage.setHeight(1080); // Set window height to 1080
    primaryStage.setMaximized(false); // Ensure the window is not maximized

    // Set the stage position to center
    primaryStage.setX(centerX - primaryStage.getWidth() / 2);
    primaryStage.setY(centerY - primaryStage.getHeight() / 2);

    primaryStage.show();
}
}
