package connect4.factory;

import connect4.piece.GamePiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;

public class PlayerFactory {
    public Player createPlayer(String name, GamePiece piece) {
        return new HumanPlayer(name, piece);
    }
}
