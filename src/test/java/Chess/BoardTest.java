package Chess;

import Szachownica.ChessBoard.Board;
import Szachownica.Figure.Bishop;
import Szachownica.Figure.MoveCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        board.addBishop(3, 4);
        assertThat(board.squareGrid[3][4].positionstate, is(Board.positionState.BISHOP));
    }

    @Test
    void testAddObstacle() {
        // Dodajemy przeszkodę na (2,2) i sprawdzamy, czy faktycznie to pole jest OBSTACLE
        board.addObstacle(2, 3);
        assertThat(board.squareGrid[2][3].positionstate, is(Board.positionState.OBSTACLE));
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
        board.bishop.forEach(b -> b.CalculateMoves(board, 4, 4));



        List<String> expectedCells = List.of(
                "x = 3 y = 3 movement = ALLOWED",
                "x = 3 y = 5 movement = ALLOWED",
                "x = 5 y = 3 movement = ALLOWED",
                "x = 5 y = 5 movement = ALLOWED"
        );

        List<String> actualCells = board.bishop.stream()
                .flatMap(b -> Arrays.stream(b.moves))       // Stream<MoveCell[]>
                .flatMap(Arrays::stream)                    // Stream<MoveCell>
                .map(MoveCell::toString)                    // Stream<String>
                .collect(Collectors.toList());

        assertThat(actualCells, hasItems(expectedCells.toArray(new String[0])));


        long allowedCount = board.bishop.stream()
                .flatMap(b -> Arrays.stream(b.moves))
                .flatMap(Arrays::stream)
                .filter(m -> m.movement == Bishop.canMove.ALLOWED)
                .count();


        assertThat(allowedCount, is(4L));
    }


    @Test
    void testAllowedMovesFullCheckForManyBishops() {
        // Ustawiamy gońca i przeszkody
        board.addBishop(4, 4);
        board.addBishop(1,1);
        board.addObstacle(2, 6);
        board.addObstacle(6, 6);
        board.addObstacle(2, 2);
        board.addObstacle(6, 2);

        // Liczymy możliwe ruchy
        board.bishop.get(0).CalculateMoves(board, 4, 4);
        board.bishop.get(1).CalculateMoves(board, 1, 1);




        List<String> expectedCells = List.of(
                "x = 3 y = 3 movement = ALLOWED",
                "x = 3 y = 5 movement = ALLOWED",
                "x = 5 y = 3 movement = ALLOWED",
                "x = 5 y = 5 movement = ALLOWED"
        );

        List<String> expectedCells2 = List.of(
                "x = 0 y = 0 movement = ALLOWED",
                "x = 0 y = 2 movement = ALLOWED",
                "x = 2 y = 0 movement = ALLOWED"
        );

        List<String> actualCells = Arrays.stream(board.bishop.get(0).moves)
                .flatMap(Arrays::stream)
                .map(MoveCell::toString)
                .collect(Collectors.toList());

        // --- Collect moves for bishop 1 only ---
        List<String> actualCells2 = Arrays.stream(board.bishop.get(1).moves)
                .flatMap(Arrays::stream)
                .map(MoveCell::toString)
                .collect(Collectors.toList());

        for (int i = 0; i < actualCells2.size(); i++) {
            System.out.println(actualCells2.get(i));
        }

        assertThat(actualCells, hasItems(expectedCells.toArray(new String[0])));
        assertThat(actualCells2, hasItems(expectedCells2.toArray(new String[0])));


        long allowedCount = Arrays.stream(board.bishop.get(0).moves)
                .flatMap(Arrays::stream)
                .filter(m -> m.movement == Bishop.canMove.ALLOWED)
                .count();

        long allowedCount2 = Arrays.stream(board.bishop.get(1).moves)
                .flatMap(Arrays::stream)
                .filter(m -> m.movement == Bishop.canMove.ALLOWED)
                .count();

        assertThat(allowedCount, is(4L));
        assertThat(allowedCount2, is(3L));

    }

    @Test
    void TryToInsertInInForbiddenPlaceBishop(){
        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> board.addBishop(322, 322)
        );
    }

    @Test
    void ManyFiguresInOnePlace(){
        board.addBishop(3, 3);
        board.addBishop(3, 3);

        board.addObstacle(2, 2);
        board.addObstacle(2, 2);
    }
}
