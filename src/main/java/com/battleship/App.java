package com.battleship;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static  Scene scene;
    private  Scene initialSetupScene;
    private  Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize initial setup UI
        Parent initialSetupRoot = FXMLLoader.load(getClass().getResource("InitialSetup.fxml"));
        initialSetupScene = new Scene(initialSetupRoot, 600, 600);

        // Initialize main stage UI
        Parent mainRoot = FXMLLoader.load(getClass().getResource("MainStage.fxml"));
        MainStage mainStage = new MainStage();
        mainScene = new Scene(mainRoot, 1920, 1080);

        // Show initial setup scene
        primaryStage.setScene(initialSetupScene);
        primaryStage.setTitle("Initial Setup");
        primaryStage.show();

        // Button handlers for initial setup
        Button onlineButton = (Button) initialSetupRoot.lookup("#onlineButton");
        Button offlineButton = (Button) initialSetupRoot.lookup("#offlineButton");

        onlineButton.setOnAction(event -> {
            // Switch to main scene for online mode
            primaryStage.setScene(mainStage.getScene());
            primaryStage.setTitle("Battleship Game - Online Mode");
            //mainStageCaller(primaryStage);
        });

        offlineButton.setOnAction(event -> {
            // Switch to main scene for offline mode
            primaryStage.setScene(mainStage.getScene());
            primaryStage.setTitle("Battleship Game - Offline Mode");
            //mainStageCaller(primaryStage);
        });
    }

    private void mainStageCaller(Stage primaryStage){
            MainStage mainStage = new MainStage();
            primaryStage.setScene(mainStage.getScene());
            primaryStage.setWidth(1920);  // Set window width to 1920
            primaryStage.setHeight(1080); // Set window height to 1080
            primaryStage.setMaximized(false); // Ensure the window is not maximized
            primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}