package connect4.strategy;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomStrategyTest {

    @Test
    void returnsValidColumnWhenMovesExist() {
        Board board = new GridBoard(6, 7);
        Player player = new HumanPlayer("P1", new RedPiece(), new HumanStrategy());
        RandomStrategy strategy = new RandomStrategy();

        int col = strategy.selectColumn(board, player);

        assertTrue(col >= 0 && col < board.getCols());
        assertFalse(board.isColumnFull(col));
    }

    @Test
    void returnsMinusOneWhenBoardIsFull() {
        Board board = new GridBoard(1, 1);
        board.dropPiece(0, 'R');

        Player player = new HumanPlayer("P1", new RedPiece(), new HumanStrategy());
        RandomStrategy strategy = new RandomStrategy();

        assertEquals(-1, strategy.selectColumn(board, player));
    }
}