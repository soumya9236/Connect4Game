package connect4.piece;

public class RedPiece implements GamePiece {
    @Override
    public char getSymbol() {
        return 'R';
    }

    @Override
    public String getColorName() {
        return "Red";
    }
}
