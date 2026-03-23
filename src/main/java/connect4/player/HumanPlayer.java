package connect4.player;

import connect4.piece.GamePiece;

public class HumanPlayer implements Player {
    private final String name;
    private final GamePiece piece;

    public HumanPlayer(String name, GamePiece piece) {
        this.name = name;
        this.piece = piece;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GamePiece getPiece() {
        return piece;
    }
}