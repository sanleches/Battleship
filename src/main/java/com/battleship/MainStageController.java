package com.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.battleship.models.AI;
import com.battleship.models.Board;
import com.battleship.models.Menus;
import com.battleship.models.Ship;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * MainStageController is responsible for managing the primary UI elements of the Battleship game.
 * It handles the layout and interactions for the game setup, including the battlefield grid,
 * ship placement controls, and various UI components such as menus, chat log, and buttons.
 */
public final class MainStageController {

    // Scene object representing the primary scene of the game
    private final Scene scene;
    //   Locale.setDefault(new Locale("en", "US"));
    ResourceBundle bundle = ResourceBundle.getBundle("com.battleship.MessagesBundle", Locale.getDefault());
    
    private String username = "def";
    // Path to the images used in the application
    private final String imagePath = "file:src/main/java/com/battleship/images/";
    // Label to display the username
    private Label usernameLabel = new Label("Username: " + username);



    // List of ships available for the player
    private List<Ship> ships;
    // Current ship being placed on the board
    private Ship currentShip;
    // Index of the current ship in the list
    private int currentShipIndex = 0;
    // GridPane for the battlefield
    private GridPane battlefieldGrid;
    //  to select the orientation of the ship (Horizontal/Vertical)
    private boolean orientationButtonFlag = true; //Flag thart ius either vertical or horizontal
    // Label to display the name of the current ship being placed
    private Label shipLabel;
    // VBox container for ships
    private VBox shipContainer;
    // Button to start the game
    private Button startGameButton;

    //AI INITIALIZATION
    public Board aiBoard = new Board(10, 10); // Assuming you initialize a board for AI
    public  AI ai = new AI(aiBoard); // Pass the board to AI

    // Initialize the game board and the list of ships
    public Board playerBoard = new Board(10, 10);


    public MainStageController() {

        /*
         * STAGE CONROLLER VARS AND INITS
         */
        // Initialize the root layout container and set the background color
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #101B27;");

        // Create and set the scene with the specified size
        scene = new Scene(root, 1920, 1080);

        // Add the menu bar to the top of the root container CONTAINS IMAGE
        Menus menus = new Menus();
        MenuBar menuBar = menus.createMenuBar();
        root.setTop(menuBar);

        // Set up the title banner with an image logo
        Image logoImage = new Image(imagePath + "logo.png");
        ImageView titleBanner = new ImageView(logoImage);
        titleBanner.setFitWidth(400);
        titleBanner.setFitHeight(250);
        titleBanner.setPreserveRatio(true);
        VBox titleBox = new VBox(titleBanner);
        titleBox.setStyle("-fx-background-color: #101B27; -fx-padding: 10px;");
        titleBox.setAlignment(Pos.CENTER);
        root.setTop(new VBox(menuBar, titleBox));

        /*
         * LEFT VBOX AND COMPONENTS
         */

        // Create the chat container
        VBox chatContainer = new VBox();
        chatContainer.setPrefSize(525, 20);
        chatContainer.setPadding(new Insets(60, 0, 0, 0));

        // Set up the chat log box
        VBox chatLogBox = new VBox();
        chatLogBox.setStyle("-fx-background-color: #3B6491; -fx-padding: 10px;");
        chatLogBox.setPrefHeight(640);

        Label chatLogLabel = new Label("Chat Log");
        chatLogLabel.setStyle("-fx-padding: 10px; -fx-text-fill: white; -fx-font-size: 16px;");
        chatLogLabel.setMaxWidth(Double.MAX_VALUE);
        chatLogLabel.setMaxHeight(Double.MAX_VALUE);
        chatLogLabel.setPrefWidth(312 - 20);
        chatLogLabel.setPrefHeight(62 - 20);
        chatLogLabel.setAlignment(Pos.TOP_LEFT);
        chatLogBox.getChildren().add(chatLogLabel);

        // Set up the chat box placeholder
        VBox chatBoxPlaceholder = new VBox();
        chatBoxPlaceholder.setPrefHeight(62);
        chatBoxPlaceholder.setStyle("-fx-background-color: #243E5A; -fx-padding: 10px;");
        Label chatBoxLabel = new Label("Chat Box");
        chatBoxLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        chatBoxLabel.setAlignment(Pos.CENTER);
        chatBoxLabel.setMaxWidth(Double.MAX_VALUE);
        chatBoxLabel.setMaxHeight(Double.MAX_VALUE);
        chatBoxLabel.setPrefWidth(312 - 20);
        chatBoxLabel.setPrefHeight(62 - 20);
        chatBoxPlaceholder.getChildren().add(chatBoxLabel);
        chatBoxPlaceholder.setAlignment(Pos.CENTER);
        applyHoverEffect(chatBoxPlaceholder, "#243E5A");

        // Add the chat log and placeholder to the chat container
        chatContainer.getChildren().addAll(chatLogBox, chatBoxPlaceholder);
        /*
         * END OF LEFT VBOX
         */
        //###############################################################################################################################

        /*
         * MIDDLE VBOX AND COMPONENTS
         */
        

        //DISPLAY SHIPS AVAILABLE
        ships = new ArrayList<>();
        ships.add(new Ship("Carrier", 4, 'C'));
        ships.add(new Ship("Battleship", 3, 'B'));
        ships.add(new Ship("Cruiser", 2, 'R'));
        ships.add(new Ship("Submarine", 3, 'S'));
        ships.add(new Ship("Destroyer", 4, 'D'));


        // Initialize the battlefield grid and set its style
        battlefieldGrid = new GridPane();
        battlefieldGrid.setAlignment(Pos.CENTER);
        battlefieldGrid.setStyle("-fx-background-color: #799FC9; -fx-border-color: #101B27;");
        initializeGameGrid(playerBoard);


        // Initialize the button with the default orientation
        Button orientationButton = new Button("Horizontal");//default is true so Horizontal
        orientationButton.setStyle("-fx-background-color: #3B6491; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");

        // Set up the action for the button to toggle orientation
        orientationButton.setOnAction(event -> {
            if (orientationButton.getText().contains("Horizontal")) {
                orientationButton.setText("Vertical");
                orientationButtonFlag = false;
            } else {
                orientationButton.setText("Horizontal");
                orientationButtonFlag = true;
            }
        });

        // Set up the username display box
        usernameLabel.setStyle("-fx-background-color: #3B6491; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");
        usernameLabel.setAlignment(Pos.CENTER);

        VBox usernameBox = new VBox(usernameLabel);
        usernameBox.setAlignment(Pos.CENTER);

        // Set up the button to change views
        Button changeViewButton = new Button("Change View");
        changeViewButton.setStyle("-fx-background-color: #3B6491; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");
        changeViewButton.setAlignment(Pos.CENTER);
        applyHoverEffect(changeViewButton, "#3B6491");

        VBox changeViewBox = new VBox(changeViewButton);
        changeViewBox.setAlignment(Pos.CENTER);

        //Create a sub box containing buttons
        HBox subCenterBox = new HBox(10); //bottom box contains functionality buttons
        subCenterBox.setAlignment(Pos.CENTER);
        subCenterBox.getChildren().addAll(orientationButton, changeViewBox);

        // Create a label for the options
        Label optionsLabel = new Label("Options:");
        optionsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;"); // Adjust the style as needed


        // Create the center layout box with the battlefield grid and other components
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);
        centerBox.getChildren().addAll(usernameBox, battlefieldGrid, optionsLabel, subCenterBox);

        // Initialize the ship container for dragging ships
        shipContainer = new VBox(0);
        shipContainer.setAlignment(Pos.CENTER);
        shipContainer.setStyle("-fx-background-color: #101B27; -fx-padding: 20px;");
        initializeShipContainer();
        /*
         * END OF MIDDLE VBOX
         */
        //###############################################################################################################################

        /*
         * RIGHT VBOX
         */


        /*
         * END OF MIDDLE VBOX
         */
        //###############################################################################################################################


        /*
         * MAIN CONTAINER CREATION
         */
        // Set up the main container with chat log and settings on the sides
        HBox mainContainer = new HBox();
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setSpacing(40);

        // Add the main components to the main container
        mainContainer.getChildren().addAll(chatContainer, centerBox, shipContainer);
        root.setCenter(mainContainer);
        /*
         * END OF MAIN CONTAINER
         */
        //###############################################################################################################################


        /*
        * POST INITIALIZATION PROCESESS
        */
        
        

        
        
        char[][] aiGrid = aiBoard.getGrid(); // Now retrieve the grid directly from the AI's board
        // String backgroundColor = (aiGrid[row][col] == 'S') ? "darkgray" : "lightblue";
        // cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: " + backgroundColor + ";");

        GameStageController game = new GameStageController(); 
        game.gameExecution(playerBoard, aiBoard, ai);


    }


    /** 
     * @return Scene
     */
    public Scene getScene() {
        return scene;
    }


    public void callAiAction(){

        ai.attack(playerBoard);
    }

    // Initialize the battlefield grid with cells and click handlers
    private void initializeGameGrid(Board playerBoard) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                VBox cell = new VBox();
                // Set all size constraints to 60x60 to lock the cell size
                cell.setMinSize(60, 60);
                cell.setPrefSize(60, 60);
                cell.setMaxSize(60, 60);
                cell.setAlignment(Pos.CENTER);
                cell.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightblue;");
                

                // Apply hover effects using the existing method
                applyHoverEffect(cell, "#ADD8E6");  // Using a lighter blue for hover
		        setupCellInteractions(cell, playerBoard);
                battlefieldGrid.add(cell, col, row);



            }
        }
    }
    

    private void setupCellInteractions(VBox cell, Board playerBoard) {
        cell.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });
        cell.setOnDragEntered(event -> {
            // Ensure row and column indices are never null by defaulting to 0
            int row = GridPane.getRowIndex(cell) != null ? GridPane.getRowIndex(cell) : 0;
            int col = GridPane.getColumnIndex(cell) != null ? GridPane.getColumnIndex(cell) : 0;
            String shipName = (String) event.getDragboard().getString();
            int lenght = getShipByName(shipName).getShipLength();
            highlightCells(row, col, lenght, true);
            event.consume();
        });
        
        cell.setOnDragExited(event -> {
            // Ensure row and column indices are never null by defaulting to 0
            int row = GridPane.getRowIndex(cell) != null ? GridPane.getRowIndex(cell) : 0;
            int col = GridPane.getColumnIndex(cell) != null ? GridPane.getColumnIndex(cell) : 0;
            String shipName = (String) event.getDragboard().getString();
            int lenght = getShipByName(shipName).getShipLength();
            highlightCells(row, col, lenght, false);
            event.consume();
        });
        cell.setOnDragDropped(event -> {
            handleDragDroppedOnCell(event, cell, playerBoard);
        });
    }

// Handle the drop of a ship image into a cell
private void handleDragDroppedOnCell(DragEvent event, VBox cell, Board playerBoard) {
    Dragboard db = event.getDragboard();
    boolean success = false;
    if (db.hasString()) {
        String shipName = db.getString();
        boolean horizontal = orientationButtonFlag;
        int finalRow = GridPane.getRowIndex(cell);
        int finalCol = GridPane.getColumnIndex(cell);
        currentShip = getShipByName(shipName);
        if (!currentShip.isPlaced() && playerBoard.placeShip(currentShip, finalRow, finalCol, horizontal)) {
            placeShipImage(currentShip, finalRow, finalCol, horizontal, playerBoard);
            reapplyHoverEffects(finalRow, finalCol, horizontal, currentShip);
            currentShip.setPlaced(true);  // Mark the ship as placed
            updateShipContainerVisibility();  // Refresh the ship container
            success = true;
        }
    }
    event.setDropCompleted(success);
    event.consume();
}


private void updateShipContainerVisibility() {
    shipContainer.getChildren().clear();  // Clear previous UI elements
    initializeShipContainer();  // Re-initialize to reflect updated placement status
}

// Update your ship placement to respect the size constraints
private void placeShipImage(Ship ship, int row, int col, boolean horizontal, Board playerBoard) {
    ImageView shipImage = new ImageView(new Image(imagePath + ship.getShipImage()));
    shipImage.setFitWidth(60);  // Set the width of the ship
    shipImage.setFitHeight(60 * ship.getSize());  // Set the height based on the ship size
    shipImage.setRotate(horizontal ? 90 : 0);  // Rotate the image based on orientation

    StackPane cellStack = new StackPane();
    cellStack.setMinSize(60 * (horizontal ? ship.getSize() : 1), 60 * (horizontal ? 1 : ship.getSize()));
    cellStack.setMaxSize(60 * (horizontal ? ship.getSize() : 1), 60 * (horizontal ? 1 : ship.getSize()));
    cellStack.getChildren().add(shipImage);
    cellStack.setAlignment(Pos.CENTER);
    cellStack.setMouseTransparent(true);  // Make the stack pane transparent to mouse events

    // Reapply hover effects to this cell stack to maintain hover behavior
    applyHoverEffect(cellStack, "#ADD8E6");

    GridPane.setConstraints(cellStack, col, row, horizontal ? ship.getSize() : 1, horizontal ? 1 : ship.getSize());
    battlefieldGrid.getChildren().add(cellStack);

    // Ensure the grid cells under the ship image continue to react to mouse events
    for (int i = 0; i < ship.getSize(); i++) {
        int currentRow = horizontal ? row : row + i;
        int currentCol = horizontal ? col + i : col;
        VBox cell = getCellByRowColumnIndex(currentRow, currentCol, battlefieldGrid);
        if (cell != null) {
            setupCellInteractions(cell,playerBoard);  // Re-setup interactions
            applyHoverEffect(cell, "#ADD8E6");  // Reapply hover effect
        }
    }
}




        
private void initializeShipContainer() {
    shipContainer.getChildren().clear();  // Clear previous children for reset
    shipContainer.setPadding(new Insets(10));  // Padding around the container
    shipContainer.setAlignment(Pos.CENTER_RIGHT);  // Ensure the container is aligned as desired
    shipContainer.setSpacing(0);  // Set space between ships

    for (Ship ship : ships) {
        if (!ship.isPlaced()) {
            Image shipImageFile = new Image(imagePath + ship.getShipImage());
            ImageView shipImage = new ImageView(shipImageFile);
            double scaledWidth = 30; // Reduce the width to ensure ships fit better
            double scaledHeight = (shipImageFile.getHeight() / shipImageFile.getWidth()) * scaledWidth;

            shipImage.setFitWidth(scaledWidth);
            shipImage.setFitHeight(scaledHeight);
            shipImage.setPreserveRatio(true);
            shipImage.setRotate(90); // Rotate the image to vertical position

            shipImage.setOnDragDetected(event -> handleDragDetected(event, ship));

            VBox imageContainer = new VBox(shipImage);
            imageContainer.setAlignment(Pos.CENTER); // Center the image within the container
            shipContainer.getChildren().add(imageContainer);
        }
    }
}


    

    // Handle drag detection for ship images
    private void handleDragDetected(MouseEvent event, Ship ship) {
        ImageView shipImage = (ImageView) event.getSource();
        Dragboard db = shipImage.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(ship.getName()); // Store the ship's name as the drag content
        db.setDragView(shipImage.getImage(), event.getX(), event.getY());
        db.setContent(content);
        event.consume();
    }


    private VBox getCellByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column && node instanceof VBox) {
                return (VBox) node;
            }
        }
        return null;
    }
    

    private Ship getShipByName(String name) {
        for (Ship ship : ships) {
            if (ship.getName().equals(name)) {
                return ship;
            }
        }
        return null;
    }


    private void highlightCells(int startRow, int startCol, int length, boolean highlight) {
        boolean horizontal = orientationButtonFlag;
        
        for (int i = 0; i < length; i++) {
            int row = horizontal ? startRow : startRow + i;
            int col = horizontal ? startCol + i : startCol;
            VBox cell = getCellByRowColumnIndex(row, col, battlefieldGrid);
            if (cell != null) {
                if (highlight) {
                    cell.setStyle("-fx-background-color: darkgray; -fx-border-color: black; -fx-border-width: 1;");
                } else {
                    cell.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 1;");
                }
            }
        }
    }
    

// Reusable hover effect method for any Pane
private void applyHoverEffect(Pane pane, String baseColor) {
    String hoverColor = lightenColor(baseColor, 0.2);
    String clickColor = lightenColor(baseColor, 0.4); // Even lighter on click
    String baseStyle = "-fx-border-color: black; -fx-border-width: 1;";

    pane.setOnMouseEntered(event -> pane.setStyle(baseStyle + "-fx-background-color: " + hoverColor + ";"));
    pane.setOnMouseExited(event -> pane.setStyle(baseStyle + "-fx-background-color: " + baseColor + ";"));
    pane.setOnMousePressed(event -> pane.setStyle(baseStyle + "-fx-background-color: " + clickColor + ";"));
    pane.setOnMouseReleased(event -> pane.setStyle(baseStyle + "-fx-background-color: " + hoverColor + ";"));
}

    

// Existing Method for buttons, now potentially reusable for other clickable items
private void applyHoverEffect(Button button, String color) {
    String hoverColor = lightenColor(color, 0.2);
    button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-border-color: #101B27; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;"));
    button.setOnMouseExited(event -> button.setStyle("-fx-background-color: " + color + "; -fx-border-color: #101B27; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;"));
}


    // Method to lighten a color by a certain factor
    private String lightenColor(String color, double factor) {
        Color c = Color.web(color);
        double red = Math.min(c.getRed() + factor, 1.0);
        double green = Math.min(c.getGreen() + factor, 1.0);
        double blue = Math.min(c.getBlue() + factor, 1.0);
        return String.format("#%02X%02X%02X",
                (int) (red * 255),
                (int) (green * 255),
                (int) (blue * 255));
    }

    private void reapplyHoverEffects(int startRow, int startCol, boolean horizontal, Ship ship) {
        int length = ship.getShipLength();
        for (int i = 0; i < length; i++) {
            int row = horizontal ? startRow : startRow + i;
            int col = horizontal ? startCol + i : startCol;
            VBox cell = getCellByRowColumnIndex(row, col, battlefieldGrid);
            if (cell != null) {
                applyHoverEffect(cell, "#ADD8E6");
            }
        }
    }
    
    

    public void setUsername(String outsideUsername) {
        usernameLabel.setText("Username: " + outsideUsername);
        this.username = outsideUsername;
    }

        // Modify the getNodeByRowColumnIndex method to ensure it handles possible nulls correctly
    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null &&
                GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }
}
