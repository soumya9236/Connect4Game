package connect4.board;

public interface Board {
    boolean dropPiece(int column, char piece);
    char getCell(int row, int col);
    boolean isColumnFull(int column);
    boolean isFull();
    int getRows();
    int getCols();
}
