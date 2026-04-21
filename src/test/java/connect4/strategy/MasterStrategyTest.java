package connect4.strategy;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MasterStrategyTest {

    @Test
    void choosesWinningMoveWhenAvailable() {
        Board board = new GridBoard(6, 7);
        board.dropPiece(0, 'R');
        board.dropPiece(0, 'R');
        board.dropPiece(0, 'R');

        Player ai = new HumanPlayer("AI", new RedPiece(), new HumanStrategy());
        MasterStrategy strategy = new MasterStrategy();

        int col = strategy.selectColumn(board, ai);

        assertEquals(0, col);
    }

    @Test
    void returnsMinusOneWhenBoardIsFull() {
        Board board = new GridBoard(1, 1);
        board.dropPiece(0, 'R');

        Player ai = new HumanPlayer("AI", new RedPiece(), new HumanStrategy());
        MasterStrategy strategy = new MasterStrategy();

        assertEquals(-1, strategy.selectColumn(board, ai));
    }
}