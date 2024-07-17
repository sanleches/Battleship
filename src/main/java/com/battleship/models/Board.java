package com.battleship.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private char[][] grid;
    private List<Ship> ships;

    public Board(int rows, int cols) {
        grid = new char[rows][cols];
        ships = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '-';
            }
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    public boolean placeShip(Ship ship, int row, int col, boolean horizontal) {
        int length = ship.getSize();
        if (horizontal) {
            if (col + length > grid[0].length) {
                return false; // Ship doesn't fit horizontally
            }
            for (int i = 0; i < length; i++) {
                if (grid[row][col + i] != '-') {
                    return false; // Collision detected
                }
            }
            for (int i = 0; i < length; i++) {
                grid[row][col + i] = ship.getSymbol();
            }
        } else {
            if (row + length > grid.length) {
                return false; // Ship doesn't fit vertically
            }
            for (int i = 0; i < length; i++) {
                if (grid[row + i][col] != '-') {
                    return false; // Collision detected
                }
            }
            for (int i = 0; i < length; i++) {
                grid[row + i][col] = ship.getSymbol();
            }
        }
        ships.add(ship);
        return true;
    }

    public void recordHit(int row, int col) {
        if (grid[row][col] != '-') {
            for (Ship ship : ships) {
                if (ship.isHit(row, col)) {
                    ship.markHit(row, col);
                    if (ship.isSunk()) {
                        System.out.println("Ship " + ship.getName() + " is sunk!");
                    }
                    break;
                }
            }
        }
    }
}
