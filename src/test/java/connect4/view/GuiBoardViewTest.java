package connect4.view;

import connect4.board.Board;
import connect4.board.GridBoard;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GuiBoardViewTest {

    @Test
    void constructorAndDisplayWork() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());

        assertDoesNotThrow(() -> {
            GuiBoardView view = new GuiBoardView(2, 2);
            Board board = new GridBoard(2, 2);
            board.dropPiece(0, 'R');
            view.display(board);
            view.dispose();
        });
    }
}