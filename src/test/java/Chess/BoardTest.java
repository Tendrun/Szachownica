package Chess;

import Szachownica.ChessBoard.Board;
import Szachownica.Figure.Bishop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        // Tworzymy szachownicę 8x8 przed każdym testem
        board = new Board(8);
    }

    @Test
    void testInitialBoardEmpty() {
        // Sprawdzamy, czy po utworzeniu ChessBoard wszystkie pola są EMPTY
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertThat("Pole [" + i + "," + j + "] powinno być EMPTY",
                    board.squareGrid[i][j].positionstate,
                    is(Board.positionState.EMPTY));
            }
        }
    }

    @Test
    void testAddBishop() {
        // Dodajemy gońca na (3,3) i sprawdzamy, czy faktycznie to pole jest BISHOP
        board.addBishop(3, 3);
        assertThat(board.squareGrid[3][3].positionstate, is(Board.positionState.BISHOP));
    }

    @Test
    void testAddObstacle() {
        // Dodajemy przeszkodę na (2,2) i sprawdzamy, czy faktycznie to pole jest OBSTACLE
        board.addObstacle(2, 2);
        assertThat(board.squareGrid[2][2].positionstate, is(Board.positionState.OBSTACLE));
    }

    @Test
    void testAllowedMovesFullCheck() {
        // Ustawiamy gońca i przeszkody
        board.addBishop(4, 4);
        board.addObstacle(2, 6);
        board.addObstacle(6, 6);
        board.addObstacle(2, 2);
        board.addObstacle(6, 2);

        // Liczymy możliwe ruchy
        board.bishop.CalculateMoves(board, 4, 4);

        // Lista pól, które *wiemy*, że powinny być ALLOWED
        List<int[]> expectedAllowed = List.of(
                new int[]{3,3}, // w górę-lewo
                new int[]{5,3}, // w dół-lewo
                new int[]{3,5}, // w górę-prawo
                new int[]{5,5}  // w dół-prawo
        );

        // Sprawdzamy w pętli, czy te pola są ALLOWED
        for (int[] coords : expectedAllowed) {
            int x = coords[0];
            int y = coords[1];
            assertThat("(" + x + "," + y + ") powinno być ALLOWED",
                    board.bishop.moves[x][y].movement, is(Bishop.canMove.ALLOWED));
        }

        // Natomiast wszystkie pozostałe pola (których nie wymieniliśmy),
        // powinny być FORBIDDEN (np. bo są poza zasięgiem lub zablokowane)
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                // Jeśli dane pole nie znajduje się w liście expectedAllowed,
                // sprawdzamy, czy jest FORBIDDEN

                final int row = x;
                final int col = y;

                boolean isExpectedAllowed = expectedAllowed.stream()
                        .anyMatch(e -> e[0] == row && e[1] == col);

                if (!isExpectedAllowed) {
                    assertThat("(" + x + "," + y + ") powinno być FORBIDDEN",
                            board.bishop.moves[x][y].movement, is(Bishop.canMove.FORBIDDEN));
                }
            }
        }
    }
}
