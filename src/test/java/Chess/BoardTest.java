package Chess;

import Szachownica.ChessBoard.Board;
import Szachownica.Figure.Bishop;
import Szachownica.Figure.MoveCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

class BoardTest {

    private Board board;
    int boardSize = 32;

    @BeforeEach
    void setUp() {
        // Tworzymy szachownicę boardSize x boardSize przed każdym testem
        board = new Board(boardSize);
    }

    @Test
    void testInitialBoardEmpty() {
        // Sprawdzamy, czy po utworzeniu ChessBoard wszystkie pola są EMPTY
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
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


        // 1) Use a 1D array of MoveCell for the expected moves
        MoveCell[] expectedMoveCells = new MoveCell[] {
                new MoveCell(Bishop.canMove.ALLOWED, 3, 5),
                new MoveCell(Bishop.canMove.ALLOWED, 5, 3),
                new MoveCell(Bishop.canMove.ALLOWED, 3, 3),
                new MoveCell(Bishop.canMove.ALLOWED, 5, 5)
        };

// 2) Flatten board.bishop.moves (which is MoveCell[][]) into a List<MoveCell>
        List<MoveCell> actualCells = Arrays.stream(board.bishop.moves)
                // for each row, produce a Stream<MoveCell>
                .flatMap(Arrays::stream)
                // collect into a single List<MoveCell>
                .collect(Collectors.toList());

        assertThat(actualCells, hasItems(expectedMoveCells));
    }

    @Test
     void TryToInsertInInForbiddenPlaceBishop(){
        board.addBishop(322, 322);
    }

    @Test
    void ManyFiguresInOnePlace(){
        board.addBishop(3, 3);
        board.addBishop(3, 3);

        board.addObstacle(2, 2);
        board.addObstacle(2, 2);
    }
}
