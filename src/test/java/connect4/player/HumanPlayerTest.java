package connect4.player;

import connect4.piece.RedPiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanPlayerTest {

    @Test
    void humanPlayerStoresCorrectName() {
        HumanPlayer player = new HumanPlayer("Player 1", new RedPiece());

        assertEquals("Player 1", player.getName());
    }

    @Test
    void humanPlayerStoresCorrectPiece() {
        RedPiece redPiece = new RedPiece();
        HumanPlayer player = new HumanPlayer("Player 1", redPiece);

        assertEquals(redPiece, player.getPiece());
    }

    @Test
    void humanPlayerPieceHasCorrectSymbol() {
        HumanPlayer player = new HumanPlayer("Player 1", new RedPiece());

        assertEquals('R', player.getPiece().getSymbol());
    }
}
