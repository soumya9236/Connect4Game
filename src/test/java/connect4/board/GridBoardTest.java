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

    @Test
    void copyGridReturnsCopyWithSameValues() {
        GridBoard board = new GridBoard(6, 7);
        board.dropPiece(0, 'R');
        board.dropPiece(1, 'B');

        char[][] copy = board.copyGrid();

        assertEquals('R', copy[5][0]);
        assertEquals('B', copy[5][1]);
        assertEquals('.', copy[5][2]);
    }

    @Test
    void copyGridDoesNotShareReferenceWithOriginal() {
        GridBoard board = new GridBoard(6, 7);
        board.dropPiece(0, 'R');

        char[][] copy = board.copyGrid();
        copy[5][0] = 'X';

        // original should be unchanged
        assertEquals('R', board.getCell(5, 0));
    }

    @Test
    void restoreGridRestoresPreviousState() {
        GridBoard board = new GridBoard(6, 7);
        board.dropPiece(0, 'R');

        char[][] snapshot = board.copyGrid();

        board.dropPiece(1, 'B');
        assertEquals('B', board.getCell(5, 1));

        board.restoreGrid(snapshot);

        assertEquals('R', board.getCell(5, 0));
        assertEquals('.', board.getCell(5, 1));
    }

    @Test
    void isFullReturnsTrueWhenAllColumnsFull() {
        GridBoard board = new GridBoard(1, 2);
        board.dropPiece(0, 'R');
        board.dropPiece(1, 'B');
        assertTrue(board.isFull());
    }

    @Test
    void isFullReturnsFalseWhenNotAllColumnsFull() {
        GridBoard board = new GridBoard(6, 7);
        board.dropPiece(0, 'R');
        assertFalse(board.isFull());
    }
}
