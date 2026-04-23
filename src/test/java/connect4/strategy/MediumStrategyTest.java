package connect4.strategy;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.piece.BluePiece;
import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MediumStrategyTest {

    @Test
    void choosesWinningMoveWhenAvailable() {
        Board board = new GridBoard(6, 7);
        board.dropPiece(0, 'R');
        board.dropPiece(0, 'R');
        board.dropPiece(0, 'R');

        Player player = new HumanPlayer("AI", new RedPiece(), new HumanStrategy());
        MediumStrategy strategy = new MediumStrategy();

        assertEquals(0, strategy.selectColumn(board, player));
    }

    @Test
    void blocksOpponentWinningMove() {
        Board board = new GridBoard(6, 7);
        board.dropPiece(2, 'B');
        board.dropPiece(2, 'B');
        board.dropPiece(2, 'B');

        Player player = new HumanPlayer("AI", new RedPiece(), new HumanStrategy());
        MediumStrategy strategy = new MediumStrategy();

        assertEquals(2, strategy.selectColumn(board, player));
    }

    @Test
    void fallsBackToRandomValidMove() {
        Board board = new GridBoard(6, 7);
        Player player = new HumanPlayer("AI", new BluePiece(), new HumanStrategy());
        MediumStrategy strategy = new MediumStrategy();

        int col = strategy.selectColumn(board, player);

        assertTrue(col >= 0 && col < board.getCols());
    }
}
