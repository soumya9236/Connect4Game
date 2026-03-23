package connect4.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BluePieceTest {

    @Test
    void bluePieceHasCorrectSymbol() {
        BluePiece bluePiece = new BluePiece();
        assertEquals('B', bluePiece.getSymbol());
    }

    @Test
    void bluePieceHasCorrectColorName() {
        BluePiece bluePiece = new BluePiece();
        assertEquals("Blue", bluePiece.getColorName());
    }
}
