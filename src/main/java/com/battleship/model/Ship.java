package com.battleship.model;

import java.util.HashSet;
import java.util.Set;

public class Ship {
    private String name;
    private int size;
    private char symbol;
    private Set<Coordinate> coordinates;
    private Set<Coordinate> hits;
    public boolean isPlaced = false; // Default is not placed


    public Ship(String name, int size, char symbol) {
        this.name = name;
        this.size = size;
        this.symbol = symbol;
        this.coordinates = new HashSet<>();
        this.hits = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isPlaced() {
        return isPlaced;
    }


    public void addCoordinate(int row, int col) {
        coordinates.add(new Coordinate(row, col));
        isPlaced = true;
    }

    public boolean isHit(int row, int col) {
        return coordinates.contains(new Coordinate(row, col));
    }

    public void markHit(int row, int col) {
        if (isHit(row, col)) {
            hits.add(new Coordinate(row, col));
        }
    }

    public boolean isSunk() {
        return hits.size() == size;
    }

    @Override
    public String toString() {
        return String.format("Ship[name=%s, size=%d, hits=%d]", name, size, hits.size());
    }

    public int getShipLength() {
        switch (name) {
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


    public String getShipImage() {
        switch (symbol) {
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
    
    
}

class Coordinate {
    private final int row;
    private final int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }
}

