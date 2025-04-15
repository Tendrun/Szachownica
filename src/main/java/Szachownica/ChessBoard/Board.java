package Szachownica.ChessBoard;

import Szachownica.Exceptions.IllegalPlacementException;
import Szachownica.Figure.Bishop;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public Square[][] squareGrid;
    private final int size;
    public List<Bishop> bishop = new ArrayList<Bishop>();
    public int i = 0;

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
        try {
            if(squareGrid[x][y].positionstate != positionState.EMPTY){
                throw new IllegalPlacementException("Nie możesz dać gońca na zajęte miejsce");
            }
            squareGrid[x][y] = new Square(positionState.BISHOP);
            bishop.add(new Bishop(size));
        }   catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Niedozwolone miejsce na położenie gońca");
        }   catch (IllegalPlacementException e) {
            System.err.println(e.getMessage());
        }
    }


    public void addObstacle(int x, int y) {
        try {
            if (squareGrid[x][y].positionstate != positionState.EMPTY) {
                throw new IllegalPlacementException("Nie możesz dać przeszkode na zajęte miejsce");
            }

            squareGrid[x][y] = new Square(positionState.OBSTACLE);
        }   catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Niedozwolone miejsce na położenie przeszkody");
        }   catch (IllegalPlacementException e) {
            System.err.println(e.getMessage());
        }
    }
}
