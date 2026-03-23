package connect4.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridBoardTest {

    @Test
    void pieceFallsToBottomOfEmptyColumn() {
        GridBoard board = new GridBoard(6, 7);

        board.dropPiece(0, 'R');

        assertEquals('R', board.getCell(5, 0));
    }

    @Test
    void piecesStackCorrectlyInSameColumn() {
        GridBoard board = new GridBoard(6, 7);

        board.dropPiece(0, 'R');
        board.dropPiece(0, 'B');

        assertEquals('R', board.getCell(5, 0));
        assertEquals('B', board.getCell(4, 0));
    }

    @Test
    void fullColumnRejectsMove() {
        GridBoard board = new GridBoard(2, 1);

        board.dropPiece(0, 'R');
        board.dropPiece(0, 'B');

        assertFalse(board.dropPiece(0, 'R'));
    }

    @Test
    void invalidColumnReturnsFalse() {
        GridBoard board = new GridBoard(6, 7);

        assertFalse(board.dropPiece(-1, 'R'));
        assertFalse(board.dropPiece(7, 'R'));
    }
}
