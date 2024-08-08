package com.battleship.model;

import java.util.Random;

public class AI {
    private final int gridSize = 10; // Assuming a 10x10 grid
    private char[][] grid; // Grid for AI ships
    private Random random;
    private Board board; // Use Board class for ship management
    
    public AI(Board board) {
        this.board = board;
        this.grid = board.getGrid(); // Directly use the board's grid
        random = new Random();
    }

    public char[][] getGrid() {
        return grid;
    }

    public void placeShips() {
        Ship[] ships = {
            new Ship("Carrier", 5, 'C'),
            new Ship("Battleship", 4, 'B'),
            new Ship("Cruiser", 3, 'R'),
            new Ship("Submarine", 3, 'S'),
            new Ship("Destroyer", 2, 'D')
        };
        for (Ship ship : ships) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(gridSize);
                int col = random.nextInt(gridSize);
                boolean horizontal = random.nextBoolean();
                placed = board.placeShip(ship, row, col, horizontal);
            }
        }
    }

    // Method for AI to attack randomly
    public void attack(Board playerBoard) {
        int row, col;
        boolean attackSuccess = false;
        do {
            row = random.nextInt(gridSize);
            col = random.nextInt(gridSize);
            attackSuccess = playerBoard.registerHit(row, col);
        } while (!attackSuccess); // Keep trying if the spot was already hit
    }
}
