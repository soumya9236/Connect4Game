package connect4.view;

import connect4.board.Board;
import connect4.board.GridBoard;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsoleBoardViewTest {

    @Test
    void displayPrintsBoard() {
        Board board = new GridBoard(2, 2);
        board.dropPiece(0, 'R');

        ConsoleBoardView view = new ConsoleBoardView();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));

        try {
            view.display(board);
        } finally {
            System.setOut(original);
        }

        String printed = out.toString();
        assertTrue(printed.contains("|"));
        assertTrue(printed.contains("R"));
        assertTrue(printed.contains("0 1"));
    }
}