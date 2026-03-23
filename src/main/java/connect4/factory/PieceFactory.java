package connect4.factory;

import connect4.piece.BluePiece;
import connect4.piece.GamePiece;
import connect4.piece.RedPiece;

public class PieceFactory {
    public GamePiece createPiece(String color) {
        if (color.equalsIgnoreCase("red")) {
            return new RedPiece();
        } else if (color.equalsIgnoreCase( "blue")) {
            return new BluePiece();
        }
        throw new IllegalArgumentException("Unknown piece color: " + color);
    }
}
