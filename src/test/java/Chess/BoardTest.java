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
import static org.hamcrest.Matchers.everyItem;
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
        List<Board.positionState> allStates = Arrays.stream(board.squareGrid)
                .flatMap(Arrays::stream)
                .map(square -> square.positionstate)
                .collect(Collectors.toList());

        assertThat(allStates, everyItem(is(Board.positionState.EMPTY)));
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


        List<String> expectedCells = List.of(
                "x = 3 y = 3 movement = ALLOWED",
                "x = 3 y = 5 movement = ALLOWED",
                "x = 5 y = 3 movement = ALLOWED",
                "x = 5 y = 5 movement = ALLOWED"
        );

        List<String> actualCells = Arrays.stream(board.bishop.moves)
                .flatMap(Arrays::stream)
                .map(MoveCell::toString)
                .collect(Collectors.toList());

        assertThat(actualCells, hasItems(expectedCells.toArray(new String[0])));


        // Check for length if are all elements are included
        // or they are not too many
        long allowedCount = Arrays.stream(board.bishop.moves)
                .flatMap(Arrays::stream)
                .filter(m -> m.movement == Bishop.canMove.ALLOWED)
                .count();

        assertThat(allowedCount, is(4L));
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
