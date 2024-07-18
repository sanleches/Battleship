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
import javafx.scene.control.ComboBox;
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



public class MainStageController {

    private final Scene scene;
 //   Locale.setDefault(new Locale("en", "US"));
    ResourceBundle bundle = ResourceBundle.getBundle("com.battleship.MessagesBundle", Locale.getDefault());
    private String username = "def";
    private final String imagePath = "file:src/main/java/com/battleship/images/";
    private Label usernameLabel = new Label("Username: " + username);

    private Board playerBoard;
    private List<Ship> ships;
    private Ship currentShip;
    private int currentShipIndex = 0;
    private GridPane battlefieldGrid;
    private ComboBox<String> orientationComboBox;
    private Label shipLabel;
    private VBox shipContainer;
    private Button startGameButton;
    private AI ai;



    public MainStageController() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #101B27;");

        scene = new Scene(root, 1920, 1080);  // Set initial size to 1920x1080

        // Initialize the board and ships
        playerBoard = new Board(10, 10);
        ships = new ArrayList<>();
        ships.add(new Ship("Carrier", 4, 'C'));
        ships.add(new Ship("Battleship", 3, 'B'));
        ships.add(new Ship("Cruiser", 2, 'R'));
        ships.add(new Ship("Submarine", 3, 'S'));
        ships.add(new Ship("Destroyer", 4, 'D'));

        // Add Menu
        Menus menus = new Menus();
        MenuBar menuBar = menus.createMenuBar();
        root.setTop(menuBar);

        // Title Banner
        Image logoImage = new Image(imagePath + "logo.png");
        ImageView titleBanner = new ImageView(logoImage);
        titleBanner.setFitWidth(400);  // Adjust width to be approximately as wide as the grid
        titleBanner.setFitHeight(250); // Ensure height does not exceed 250 pixels
        titleBanner.setPreserveRatio(true);
        VBox titleBox = new VBox(titleBanner);
        titleBox.setStyle("-fx-background-color: #101B27; -fx-padding: 10px;"); // Dark blue
        titleBox.setAlignment(Pos.CENTER);
        root.setTop(new VBox(menuBar, titleBox));

        // Battlefield Grid
        battlefieldGrid = new GridPane();
        battlefieldGrid.setAlignment(Pos.CENTER);
        battlefieldGrid.setStyle("-fx-background-color: #799FC9; -fx-border-color: #101B27;");
        initializeGameGrid();

        // Ship Placement Controls
        shipLabel = new Label("Placing: " + ships.get(currentShipIndex).getName());
        shipLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        orientationComboBox = new ComboBox<>();
        orientationComboBox.getItems().addAll("Horizontal", "Vertical");
        orientationComboBox.setValue("Horizontal");

        VBox shipPlacementBox = new VBox(10, shipLabel, orientationComboBox);
        shipPlacementBox.setAlignment(Pos.CENTER);
        shipPlacementBox.setStyle("-fx-background-color: #101B27; -fx-padding: 20px;");

        // Username Box
        usernameLabel.setStyle("-fx-background-color: #3B6491; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;"); // Increase padding and font size
        usernameLabel.setAlignment(Pos.CENTER);

        VBox usernameBox = new VBox(usernameLabel);
        usernameBox.setAlignment(Pos.CENTER);

        // Change View Button
        Button changeViewButton = new Button("Change View");
        changeViewButton.setStyle("-fx-background-color: #3B6491; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;"); // Increase padding and font size
        changeViewButton.setAlignment(Pos.CENTER);
        applyHoverEffect(changeViewButton, "#3B6491");

        VBox changeViewBox = new VBox(changeViewButton);
        changeViewBox.setAlignment(Pos.CENTER);

        // Center layout with battlefield grid and placeholders
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);  // Adjust spacing between elements

        centerBox.getChildren().addAll(usernameBox, battlefieldGrid, shipPlacementBox, changeViewBox);

        // Ship Container for Dragging Ships
        shipContainer = new VBox(0);
        shipContainer.setAlignment(Pos.CENTER);
        shipContainer.setStyle("-fx-background-color: #101B27; -fx-padding: 20px;");
        initializeShipContainer();

        // Side Containers for Chat Log and Settings
        HBox mainContainer = new HBox();
        mainContainer.setAlignment(Pos.TOP_CENTER);  // Adjust alignment to top center
        mainContainer.setSpacing(40);

        // Chat Log and Chat Box
        VBox chatContainer = new VBox();
        chatContainer.setPrefSize(525, 20);  // Set width directly on VBox
        chatContainer.setPadding(new Insets(60, 0, 0, 0));  // 60 pixels padding from the top

        VBox chatLogBox = new VBox();
        chatLogBox.setStyle("-fx-background-color: #3B6491; -fx-padding: 10px;");
        chatLogBox.setPrefHeight(640);  // Adjust height to fit remaining space after adding chat box

        Label chatLogLabel = new Label("Chat Log");
        chatLogLabel.setStyle("-fx-padding: 10px; -fx-text-fill: white; -fx-font-size: 16px;");  // Increase font size
        chatLogLabel.setMaxWidth(Double.MAX_VALUE);
        chatLogLabel.setMaxHeight(Double.MAX_VALUE);
        chatLogLabel.setPrefWidth(312 - 20);  // Adjust for padding
        chatLogLabel.setPrefHeight(62 - 20);  // Adjust for padding
        chatLogLabel.setAlignment(Pos.TOP_LEFT);
        chatLogBox.getChildren().add(chatLogLabel);

        VBox chatBoxPlaceholder = new VBox();
        chatBoxPlaceholder.setPrefHeight(62);  // Adjust height directly on VBox
        chatBoxPlaceholder.setStyle("-fx-background-color: #243E5A; -fx-padding: 10px;");
        Label chatBoxLabel = new Label("Chat Box");
        chatBoxLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");  // Increase font size
        chatBoxLabel.setAlignment(Pos.CENTER);
        chatBoxLabel.setMaxWidth(Double.MAX_VALUE);
        chatBoxLabel.setMaxHeight(Double.MAX_VALUE);
        chatBoxLabel.setPrefWidth(312 - 20);  // Adjust for padding
        chatBoxLabel.setPrefHeight(62 - 20);  // Adjust for padding
        chatBoxPlaceholder.getChildren().add(chatBoxLabel);
        chatBoxPlaceholder.setAlignment(Pos.CENTER);  // Align content center to ensure label is centered within the box

        chatContainer.getChildren().addAll(chatLogBox, chatBoxPlaceholder);
        applyHoverEffect(chatBoxPlaceholder, "#243E5A");

        mainContainer.getChildren().addAll(chatContainer, centerBox, shipContainer);
        root.setCenter(mainContainer);

        
    }

    public Scene getScene() {
        return scene;
    }


    // Initialize the battlefield grid with cells and click handlers
    private void initializeGameGrid() {
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
		        setupCellInteractions(cell);
                battlefieldGrid.add(cell, col, row);
            }
        }
    }
    

    private void setupCellInteractions(VBox cell) {
        cell.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });
        cell.setOnDragEntered(event -> {
            // Ensure row and column indices are never null by defaulting to 0
            int row = GridPane.getRowIndex(cell) != null ? GridPane.getRowIndex(cell) : 0;
            int col = GridPane.getColumnIndex(cell) != null ? GridPane.getColumnIndex(cell) : 0;
            String shipName = (String) event.getDragboard().getString();
            highlightCells(row, col, shipName, true);
            event.consume();
        });
        
        cell.setOnDragExited(event -> {
            // Ensure row and column indices are never null by defaulting to 0
            int row = GridPane.getRowIndex(cell) != null ? GridPane.getRowIndex(cell) : 0;
            int col = GridPane.getColumnIndex(cell) != null ? GridPane.getColumnIndex(cell) : 0;
            String shipName = (String) event.getDragboard().getString();
            highlightCells(row, col, shipName, false);
            event.consume();
        });
        cell.setOnDragDropped(event -> {
            handleDragDroppedOnCell(event, cell);
        });
    }

// Handle the drop of a ship image into a cell
private void handleDragDroppedOnCell(DragEvent event, VBox cell) {
    Dragboard db = event.getDragboard();
    boolean success = false;
    if (db.hasString()) {
        String shipName = db.getString();
        boolean horizontal = orientationComboBox.getValue().equals("Horizontal");
        int finalRow = GridPane.getRowIndex(cell);
        int finalCol = GridPane.getColumnIndex(cell);
        currentShip = getShipByName(shipName);
        if (!currentShip.isPlaced() && playerBoard.placeShip(currentShip, finalRow, finalCol, horizontal)) {
            placeShipImage(currentShip, finalRow, finalCol, horizontal);
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
private void placeShipImage(Ship ship, int row, int col, boolean horizontal) {
    ImageView shipImage = new ImageView(new Image(imagePath + getShipImage(ship.getSymbol())));
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
            setupCellInteractions(cell);  // Re-setup interactions
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
            Image shipImageFile = new Image(imagePath + getShipImage(ship.getSymbol()));
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
    

    private int getShipLength(String shipName) {
        switch (shipName) {
            case "Carrier":
                return 4;
            case "Battleship":
                return 3;
            case "Cruiser":
                return 2;
            case "Submarine":
                return 3;
            case "Destroyer":
                return 4;
            default:
                return 0;
        }
    }


    private String getShipImage(char shipSymbol) {
        switch (shipSymbol) {
            case 'C':
                return "carrier.png";
            case 'B':
                return "default.png"; // Assuming battleship.png is not available and default.png is used instead
            case 'R':
                return "cruiser.png";
            case 'S':
                return "submarine.png";
            case 'D':
                return "destroyer.png";
            default:
                return "default.png";
        }
    }

    private Ship getShipByName(String name) {
        for (Ship ship : ships) {
            if (ship.getName().equals(name)) {
                return ship;
            }
        }
        return null;
    }


    private void highlightCells(int startRow, int startCol, String shipName, boolean highlight) {
        int length = getShipLength(shipName);
        boolean horizontal = orientationComboBox.getValue().equals("Horizontal");
        
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
        int length = getShipLength(ship.getName());
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
