package connect4.strategy;

import connect4.board.Board;

public interface WinStrategy {
    boolean checkWin(Board board, char piece);
}