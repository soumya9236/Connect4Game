package connect4.player;

import connect4.piece.GamePiece;
import connect4.strategy.MoveStrategy;

public interface Player {
    String getName();
    GamePiece getPiece();
    MoveStrategy getStrategy();
}
