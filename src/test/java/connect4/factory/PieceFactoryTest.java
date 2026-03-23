package connect4.factory;

import connect4.piece.BluePiece;
import connect4.piece.GamePiece;
import connect4.piece.RedPiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceFactoryTest {

    @Test
    void createPieceReturnsRedPieceForRedInput() {
        PieceFactory factory = new PieceFactory();

        GamePiece piece = factory.createPiece("red");

        assertInstanceOf(RedPiece.class, piece);
    }

    @Test
    void createPieceReturnsBluePieceForBlueInput() {
        PieceFactory factory = new PieceFactory();

        GamePiece piece = factory.createPiece("blue");

        assertInstanceOf(BluePiece.class, piece);
    }

    @Test
    void createPieceIgnoresCapitalization() {
        PieceFactory factory = new PieceFactory();

        GamePiece piece = factory.createPiece("ReD");

        assertInstanceOf(RedPiece.class, piece);
    }

    @Test
    void createPieceThrowsExceptionForInvalidColor() {
        PieceFactory factory = new PieceFactory();

        assertThrows(IllegalArgumentException.class, () -> factory.createPiece("green"));
    }
}