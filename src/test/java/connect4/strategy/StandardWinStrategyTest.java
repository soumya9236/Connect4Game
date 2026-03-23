package connect4.strategy;

import connect4.board.GridBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StandardWinStrategyTest {

    @Test
    void detectsHorizontalWin() {
        GridBoard board = new GridBoard(6, 7);

        board.dropPiece(0, 'R');
        board.dropPiece(1, 'R');
        board.dropPiece(2, 'R');
        board.dropPiece(3, 'R');

        StandardWinStrategy strategy = new StandardWinStrategy();

        assertTrue(strategy.checkWin(board, 'R'));
    }

    @Test
    void detectsVerticalWin() {
        GridBoard board = new GridBoard(6, 7);

        board.dropPiece(0, 'B');
        board.dropPiece(0, 'B');
        board.dropPiece(0, 'B');
        board.dropPiece(0, 'B');

        StandardWinStrategy strategy = new StandardWinStrategy();

        assertTrue(strategy.checkWin(board, 'B'));
    }

    @Test
    void detectsNoWinWhenLessThanFour() {
        GridBoard board = new GridBoard(6, 7);

        board.dropPiece(0, 'R');
        board.dropPiece(1, 'R');
        board.dropPiece(2, 'R');

        StandardWinStrategy strategy = new StandardWinStrategy();

        assertFalse(strategy.checkWin(board, 'R'));
    }
}
