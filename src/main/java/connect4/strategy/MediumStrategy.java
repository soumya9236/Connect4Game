package connect4.strategy;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Medium difficulty computer strategy.
 * Tries to win if it can, blocks the opponent if they are about to win,
 * otherwise picks a random column.
 */
public class MediumStrategy implements MoveStrategy {

    private final Random random = new Random();

    @Override
    public int selectColumn(Board board, Player player) {
        char mySymbol = player.getPiece().getSymbol();
        char opponentSymbol = getOpponentSymbol(mySymbol);

        // 1. Win if possible
        int winningCol = findWinningMove(board, mySymbol);
        if (winningCol != -1) return winningCol;

        // 2. Block opponent from winning
        int blockingCol = findWinningMove(board, opponentSymbol);
        if (blockingCol != -1) return blockingCol;

        // 3. Random fallback
        return pickRandom(board);
    }

    private int findWinningMove(Board board, char symbol) {
        WinStrategy winCheck = new StandardWinStrategy();

        for (int col = 0; col < board.getCols(); col++) {
            if (board.isColumnFull(col)) continue;

            GridBoard copy = copyBoard(board);
            copy.dropPiece(col, symbol);

            if (winCheck.checkWin(copy, symbol)) {
                return col;
            }
        }
        return -1;
    }

    private int pickRandom(Board board) {
        List<Integer> valid = new ArrayList<>();
        for (int c = 0; c < board.getCols(); c++) {
            if (!board.isColumnFull(c)) valid.add(c);
        }
        if (valid.isEmpty()) return -1;
        return valid.get(random.nextInt(valid.size()));
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

    private char getOpponentSymbol(char mine) {
        return (mine == 'R') ? 'B' : 'R';
    }
}