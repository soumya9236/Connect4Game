package connect4.strategy;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanStrategyTest {

    @Test
    void selectColumnReadsValidNumber() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("3\n".getBytes()));

        HumanStrategy strategy = new HumanStrategy(scanner);
        Board board = new GridBoard(6, 7);
        Player player = new HumanPlayer("Player 1", new RedPiece(), strategy);

        int col = strategy.selectColumn(board, player);

        assertEquals(3, col);
    }

    @Test
    void selectColumnSkipsInvalidInputThenReadsNumber() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("abc\n2\n".getBytes()));

        HumanStrategy strategy = new HumanStrategy(scanner);
        Board board = new GridBoard(6, 7);
        Player player = new HumanPlayer("Player 1", new RedPiece(), strategy);

        int col = strategy.selectColumn(board, player);

        assertEquals(2, col);
    }
}
