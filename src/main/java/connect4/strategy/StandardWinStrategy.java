package connect4.strategy;

import connect4.board.Board;

public class StandardWinStrategy implements WinStrategy {

    @Override
    public boolean checkWin(Board board, char piece) {
        int rows = board.getRows();
        int cols = board.getCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board.getCell(r, c) != piece) {
                    continue;
                }

                if (checkDirection(board, r, c, 0, 1, piece)) return true;   // horizontal
                if (checkDirection(board, r, c, 1, 0, piece)) return true;   // vertical
                if (checkDirection(board, r, c, 1, 1, piece)) return true;   // diagonal down-right
                if (checkDirection(board, r, c, 1, -1, piece)) return true;  // diagonal down-left
            }
        }

        return false;
    }

    private boolean checkDirection(Board board, int row, int col, int rowStep, int colStep, char piece) {
        for (int i = 0; i < 4; i++) {
            int newRow = row + i * rowStep;
            int newCol = col + i * colStep;

            if (newRow < 0 || newRow >= board.getRows() || newCol < 0 || newCol >= board.getCols()) {
                return false;
            }

            if (board.getCell(newRow, newCol) != piece) {
                return false;
            }
        }

        return true;
    }
}
