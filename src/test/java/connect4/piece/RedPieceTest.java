package connect4.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedPieceTest {

    @Test
    void redPieceHasCorrectSymbol() {
        RedPiece redPiece = new RedPiece();
        assertEquals('R', redPiece.getSymbol());
    }

    @Test
    void redPieceHasCorrectColorName() {
        RedPiece redPiece = new RedPiece();
        assertEquals("Red", redPiece.getColorName());
    }
}
