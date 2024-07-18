package com.battleship.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Board {
    private char[][] grid;
    private List<Ship> ships;
    private Random random;
    private Set<Coordinate> attackedCoordinates; // Tracks all coordinates that have been attacked


    public Board(int rows, int cols) {
        grid = new char[rows][cols];
        ships = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '-';
            }
        }
    }

    public char[][] getGrid() {
        return grid; // Provide access to the grid array
    }

    public boolean placeShip(Ship ship, int row, int col, boolean horizontal) {
        int length = ship.getSize();
    
        if (horizontal) {
            if (col + length > grid[0].length) {
                System.out.println("Failed to place horizontally: Ship out of bounds. Size: " + length + " at column: " + col);
                return false; // Ship doesn't fit horizontally
            }
            for (int i = 0; i < length; i++) {
                if (grid[row][col + i] != '-') {
                    System.out.println("Failed to place horizontally: Collision detected at (" + row + "," + (col + i) + ")");
                    return false; // Collision detected
                }
            }
            for (int i = 0; i < length; i++) {
                grid[row][col + i] = ship.getSymbol(); // Mark the ship's position with its symbol
            }
        } else {
            if (row + length > grid.length) {
                System.out.println("Failed to place vertically: Ship out of bounds. Size: " + length + " at row: " + row);
                return false; // Ship doesn't fit vertically
            }
            for (int i = 0; i < length; i++) {
                if (grid[row + i][col] != '-') {
                    System.out.println("Failed to place vertically: Collision detected at (" + (row + i) + "," + col + ")");
                    return false; // Collision detected
                }
            }
            for (int i = 0; i < length; i++) {
                grid[row + i][col] = ship.getSymbol(); // Mark the ship's position with its symbol
            }
        }
        System.out.println("Ship placed successfully: " + ship.getName() + " at (" + row + "," + col + ") " + (horizontal ? "horizontally" : "vertically"));
        ships.add(ship);
        return true;
    }
    
    public boolean registerHit(int row, int col) {
        if (grid[row][col] == '-') {
            grid[row][col] = 'M'; // Miss
            System.out.println("Miss at (" + row + "," + col + ")");
            return true;
        } else if (grid[row][col] != 'M' && grid[row][col] != 'H') {
            grid[row][col] = 'H'; // Hit
            System.out.println("Hit at (" + row + "," + col + ")");
            for (Ship ship : ships) {
                if (ship.isHit(row, col)) {
                    ship.markHit(row, col);
                    if (ship.isSunk()) {
                        System.out.println("Ship " + ship.getName() + " is sunk!");
                        if (allShipsSunk()) {
                            System.out.println("Game Ended! All ships are sunk!");
                        }
                    }
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllShipsPlaced() {
        for (Ship ship : ships) {
            if (!ship.isPlaced()) {
                return false;
            }
        }
        return true;
    }
}
