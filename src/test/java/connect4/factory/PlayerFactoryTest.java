package connect4.factory;

import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import connect4.strategy.HumanStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PlayerFactoryTest {

    @Test
    void createPlayerReturnsHumanPlayer() {
        PlayerFactory factory = new PlayerFactory();

        Player player = factory.createPlayer("Player 1", new RedPiece(),new HumanStrategy());

        assertInstanceOf(HumanPlayer.class, player);
    }

    @Test
    void createdPlayerHasCorrectName() {
        PlayerFactory factory = new PlayerFactory();

        Player player = factory.createPlayer("Player 1", new RedPiece(),new HumanStrategy());

        assertEquals("Player 1", player.getName());
    }

    @Test
    void createdPlayerHasCorrectPiece() {
        PlayerFactory factory = new PlayerFactory();
        RedPiece redPiece = new RedPiece();

        Player player = factory.createPlayer("Player 1", redPiece,new HumanStrategy());

        assertEquals(redPiece, player.getPiece());
    }
}