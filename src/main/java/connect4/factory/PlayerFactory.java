package connect4.factory;

import connect4.piece.GamePiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import connect4.strategy.MoveStrategy;

public class PlayerFactory {
    public Player createPlayer(String name, GamePiece piece, MoveStrategy strategy ) {
        return new HumanPlayer(name, piece, strategy);
    }
}
