package connect4.strategy;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.player.Player;

/**
 * Hard difficulty computer. Thinks ahead several moves to pick the best play.
 */
public class HardStrategy implements MoveStrategy {

    private static final int MAX_DEPTH  =  5;
    private static final int WIN_SCORE  =  1_000_000;
    private static final int LOSE_SCORE = -1_000_000;

    private char aiSymbol;
    private char humanSymbol;

    @Override
    public int selectColumn(Board board, Player player) {
        aiSymbol    = player.getPiece().getSymbol();
        humanSymbol = (aiSymbol == 'R') ? 'B' : 'R';

        int bestCol   = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int col = 0; col < board.getCols(); col++) {
            if (board.isColumnFull(col)) continue;

            GridBoard copy = copyBoard(board);
            copy.dropPiece(col, aiSymbol);

            int score = minimax(copy, MAX_DEPTH - 1, false,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (score > bestScore) {
                bestScore = score;
                bestCol   = col;
            }
        }

        return bestCol;
    }

    private int minimax(GridBoard board, int depth,
                        boolean isMaximising, int alpha, int beta) {

        WinStrategy winCheck = new StandardWinStrategy();

        if (winCheck.checkWin(board, aiSymbol))    return WIN_SCORE  + depth;
        if (winCheck.checkWin(board, humanSymbol)) return LOSE_SCORE - depth;
        if (board.isFull() || depth == 0)          return evaluate(board);

        if (isMaximising) {
            int best = Integer.MIN_VALUE;
            for (int col = 0; col < board.getCols(); col++) {
                if (board.isColumnFull(col)) continue;
                GridBoard copy = copyBoard(board);
                copy.dropPiece(col, aiSymbol);
                best  = Math.max(best, minimax(copy, depth - 1, false, alpha, beta));
                alpha = Math.max(alpha, best);
                if (beta <= alpha) break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int col = 0; col < board.getCols(); col++) {
                if (board.isColumnFull(col)) continue;
                GridBoard copy = copyBoard(board);
                copy.dropPiece(col, humanSymbol);
                best = Math.min(best, minimax(copy, depth - 1, true, alpha, beta));
                beta = Math.min(beta, best);
                if (beta <= alpha) break;
            }
            return best;
        }
    }

    private int evaluate(GridBoard board) {
        int score = 0;
        int rows = board.getRows();
        int cols = board.getCols();

        // centre column
        int centreCol = cols / 2;
        for (int r = 0; r < rows; r++) {
            if (board.getCell(r, centreCol) == aiSymbol) score += 3;
        }

        // Horizontal
        for (int r = 0; r < rows; r++)
            for (int c = 0; c <= cols - 4; c++)
                score += scoreWindow(board, r, c, 0, 1);

        // Vertical
        for (int r = 0; r <= rows - 4; r++)
            for (int c = 0; c < cols; c++)
                score += scoreWindow(board, r, c, 1, 0);

        // Diagonal down-right
        for (int r = 0; r <= rows - 4; r++)
            for (int c = 0; c <= cols - 4; c++)
                score += scoreWindow(board, r, c, 1, 1);

        // Diagonal down-left
        for (int r = 0; r <= rows - 4; r++)
            for (int c = cols - 1; c >= 3; c--)
                score += scoreWindow(board, r, c, 1, -1);

        return score;
    }

    private int scoreWindow(GridBoard board, int startRow, int startCol,
                            int rowStep, int colStep) {
        int aiCount    = 0;
        int humanCount = 0;
        int emptyCount = 0;

        for (int i = 0; i < 4; i++) {
            char cell = board.getCell(startRow + i * rowStep,
                    startCol + i * colStep);
            if      (cell == aiSymbol)    aiCount++;
            else if (cell == humanSymbol) humanCount++;
            else                          emptyCount++;
        }

        if (humanCount > 0 && aiCount > 0) return 0;

        if      (aiCount == 3 && emptyCount == 1)    return  50;
        else if (aiCount == 2 && emptyCount == 2)    return  10;
        else if (aiCount == 1 && emptyCount == 3)    return   1;
        else if (humanCount == 3 && emptyCount == 1) return -80;
        else if (humanCount == 2 && emptyCount == 2) return -10;

        return 0;
    }

    // iterates each column from bottom row up so dropPiece place in correct row
    private GridBoard copyBoard(Board board) {
        GridBoard copy = new GridBoard(board.getRows(), board.getCols());
        for (int c = 0; c < board.getCols(); c++) {
            for (int r = board.getRows() - 1; r >= 0; r--) {
                char cell = board.getCell(r, c);
                if (cell != '.') {
                    copy.dropPiece(c, cell);
                }
            }
        }
        return copy;
    }
}