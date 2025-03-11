package Szachownica.ChessBoard;

import Szachownica.Figure.Bishop;

public class Board {

    public Square[][] squareGrid;
    private final int size;
    public Bishop bishop;

    public enum positionState {
        EMPTY,
        OBSTACLE,
        BISHOP,
    }


    public Board(int size) {

        this.size = size;
        squareGrid = new Square[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                squareGrid[x][y] = new Square(positionState.EMPTY);
            }
        }
    }

    public void addBishop(int x, int y) {
        squareGrid[x][y] = new Square(positionState.BISHOP);
        bishop = new Bishop(size);
    }


    public void addObstacle(int x, int y) {
        squareGrid[x][y] = new Square(positionState.OBSTACLE);
    }
}
