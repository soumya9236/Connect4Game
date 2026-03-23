package connect4.piece;

public class BluePiece implements GamePiece {
    @Override
    public char getSymbol() {
        return 'B';
    }

    @Override
    public String getColorName() {
        return "Blue";
    }
}
