package main;

import static main.FieldParameters.FIELD_SIZE;

public class Square {

    public int x;
    public int y;

    public Square(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    public static Square[] getNeighbors(int x, int y) {
        Square[] neighbors = new Square[2];
        if (x % FIELD_SIZE == 0) {
            neighbors[0] = positionToSquare(x, y);
            if (neighbors[0].squareToPosition()[1] < y) {
                neighbors[1] = new Square(neighbors[0].x, neighbors[0].y + 1);
                return neighbors;
            }
            else if (neighbors[0].squareToPosition()[1] > y) {
                neighbors[1] = new Square(neighbors[0].x, neighbors[0].y - 1);
                return neighbors;
            }
            else {
                System.err.println("Error: getNeighbors 1");
                throw new RuntimeException();
            }
        }
        else if (y % FIELD_SIZE == 0) {
            neighbors[0] = positionToSquare(x, y);
            if (neighbors[0].squareToPosition()[0] < x) {
                neighbors[1] = new Square(neighbors[0].x + 1, neighbors[0].y);
                return neighbors;
            }
            else if (neighbors[0].squareToPosition()[0] > x) {
                neighbors[1] = new Square(neighbors[0].x - 1, neighbors[0].y);
                return neighbors;
            }
            else {
                System.err.println("Error: getNeighbors 2");
                throw new RuntimeException();
            }
        }
        System.err.println("Error: getNeighbors 3");
        throw new RuntimeException();
    }

    public static Square positionToSquare(int x, int y) {
        int squareX = x / FIELD_SIZE;
        int squareY = y / FIELD_SIZE;
        Square square = new Square(squareX, squareY);
        return square;
    }

    public int[] squareToPosition() {
        return new int[] {x * FIELD_SIZE, y * FIELD_SIZE};
    }
}
