package Szachownica.Figure;

import Szachownica.ChessBoard.Board;

public class Bishop {
    public MoveCell[][] moves;
    private final int size;


    public enum canMove {
        FORBIDDEN,
        ALLOWED,
    }

    public Bishop(int size) {
        this.size = size;

        // Stwórz wirtualną plansze gdzie są wypisywane dozwolone i niedozwolone ruchy
        moves = new MoveCell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                moves[x][y] = new MoveCell(canMove.FORBIDDEN, x, y);
            }
        }
    }

    public void CalculateMoves(Board boardState,
                               int startX, int startY) {
        // 1. Wyczyszczamy Moves (wszystkie pola na FORBIDDEN)
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                moves[x][y].movement = canMove.FORBIDDEN;
            }
        }

        // 2. Definiujemy kierunki poruszania się po przekątnych:
        //    ↘ (dx=1, dy=1), ↗ (dx=-1, dy=1), ↙ (dx=1, dy=-1), ↖ (dx=-1, dy=-1)
        int[][] directions = {
                {1, 1},  // w dół, w prawo
                {1, -1},  // w dół, w lewo
                {-1, 1},  // w górę, w prawo
                {-1, -1}   // w górę, w lewo
        };

        // 3. Dla każdej z 4 przekątnych idziemy po kolei, aż trafimy na przeszkodę lub wyjdziemy poza mapę
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            // Zaczynamy od pozycji gonca
            int currentX = startX;
            int currentY = startY;

            // Ruszamy się w pętli, aż "wyjdziemy" poza planszę lub spotkamy przeszkodę
            while (true) {
                currentX += dx;
                currentY += dy;

                // Sprawdzamy, czy jesteśmy nadal w obszarze planszy
                if (currentX < 0 || currentX >= size || currentY < 0 || currentY >= size) {
                    break; // Poza mapę - kończymy w tym kierunku
                }

                // Jeśli pole to przeszkoda - kończymy w tym kierunku (nie można przejść dalej)
                if (boardState.squareGrid[currentX][currentY].positionstate == Board.positionState.OBSTACLE) {
                    break;
                }

                // Jeśli to nie przeszkoda, to można stanąć
                moves[currentX][currentY].movement = canMove.ALLOWED;
            }
        }
    }

}
