package com.battleship.models;

import java.util.List;

public class Player {
    private String name;
    private Board board;
    private List<Ship> ships;

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
}
