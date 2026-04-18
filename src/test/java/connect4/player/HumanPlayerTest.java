package connect4.player;

import connect4.piece.RedPiece;
import connect4.strategy.HumanStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanPlayerTest {

    @Test
    void humanPlayerStoresCorrectName() {
        HumanPlayer player = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());

        assertEquals("Player 1", player.getName());
    }

    @Test
    void humanPlayerStoresCorrectPiece() {
        RedPiece redPiece = new RedPiece();
        HumanPlayer player = new HumanPlayer("Player 1", redPiece,new HumanStrategy());

        assertEquals(redPiece, player.getPiece());
    }

    @Test
    void humanPlayerPieceHasCorrectSymbol() {
        HumanPlayer player = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());

        assertEquals('R', player.getPiece().getSymbol());
    }
}
