package connect4.strategy;

import connect4.board.Board;
import connect4.player.Player;

public interface MoveStrategy {
    int selectColumn(Board board, Player player);
}