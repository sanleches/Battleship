package com.battleship.models;

import java.util.List;

public class Player {
    private String name;
    private Board board;
    private List<Ship> ships;
    private boolean isReadyToStartGame = false;

    public Player(String name, int rows, int cols) {
        this.name = name;
        this.board = new Board(rows, cols);
        initializeShips(); // Initialize ships on the board
    }

    private void initializeShips() {
        // You can define these ship types and sizes based on your game rules
        ships.add(new Ship("Carrier", 5, 'C'));
        ships.add(new Ship("Battleship", 4, 'B'));
        ships.add(new Ship("Cruiser", 3, 'R'));
        ships.add(new Ship("Submarine", 3, 'S'));
        ships.add(new Ship("Destroyer", 2, 'D'));
    }

    public boolean placeShip(Ship ship, int row, int col, boolean horizontal) {
        return board.placeShip(ship, row, col, horizontal);
    }

    public void attack(Board aiBoard, int row, int col) {
        if (aiBoard.getGrid()[row][col] == '-' || aiBoard.getGrid()[row][col] == 'S') {
            boolean hitSuccess = aiBoard.registerHit(row, col);
            if (hitSuccess) {
                System.out.println("Attack was successful at (" + row + ", " + col + ")");
            } else {
                System.out.println("Attack failed at (" + row + ", " + col + ") due to an invalid position or previous hit.");
            }
        }
    }

    public boolean isGameOver() {
        return board.allShipsSunk();
    }

    public Board getBoard() {
        return board;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public String getName() {
        return name;
    }

    // Method to finalize ship placement and attempt to start the game
    public void finalizeShipPlacement() {
        if (areAllShipsPlaced()) {
            isReadyToStartGame = true;
            startGame();  // Proceed to start the game
        } else {
            System.out.println("Not all ships are placed. Please place all ships to start the game.");
        }
    }

    private boolean areAllShipsPlaced() {
        return board.areAllShipsPlaced();
    }

    // Starts the game if all ships are placed
    private void startGame() {
        if (isReadyToStartGame) {
            System.out.println("Starting game...");
            // Additional logic to transition into game playing phase
            // For example: Set game state, initialize game timer, etc.
        } else {
            System.out.println("Cannot start game. Ensure all ships are placed.");
        }
    }
}


