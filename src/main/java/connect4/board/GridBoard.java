package connect4.board;

public class GridBoard implements Board{
    private final int rows;
    private final int cols;
    private final char[][] grid;

    public GridBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = '.';
            }
        }
    }

    @Override
    public boolean dropPiece(int column, char piece) {
        if (column < 0 || column >= cols || isColumnFull(column)) {
            return false;
        }

        for (int r = rows - 1; r >= 0; r--) {
            if (grid[r][column] == '.') {
                grid[r][column] = piece;
                return true;
            }
        }

        return false;
    }

    @Override
    public char getCell(int row, int col) {
        return grid[row][col];
    }

    @Override
    public boolean isColumnFull(int column) {
        return grid[0][column] != '.';
    }

    @Override
    public boolean isFull() {
        for (int c = 0; c < cols; c++) {
            if (!isColumnFull(c)) return false;
        }
        return true;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    public char[][] copyGrid() {
        char[][] copy = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                copy[r][c] = grid[r][c];
            }
        }
        return copy;
    }

    public void restoreGrid(char[][] savedGrid) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = savedGrid[r][c];
            }
        }
    }

}
