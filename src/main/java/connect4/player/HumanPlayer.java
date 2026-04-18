package connect4.player;

import connect4.piece.GamePiece;
import connect4.strategy.MoveStrategy;

public class HumanPlayer implements Player {
    private final String name;
    private final GamePiece piece;
    private final MoveStrategy strategy;

    public HumanPlayer(String name, GamePiece piece, MoveStrategy strategy ) {
        this.name = name;
        this.piece = piece;
        this.strategy = strategy;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GamePiece getPiece() {
        return piece;
    }
    @Override
    public MoveStrategy getStrategy() {
        return strategy;
    }
}