package connect4.strategy;

import connect4.board.Board;
import connect4.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyStrategy implements MoveStrategy {
    private final Random random = new Random();

    @Override
    public int selectColumn(Board board, Player player) {
        List<Integer> validColumns = new ArrayList<>();

        for (int c = 0; c < board.getCols(); c++) {
            if (!board.isColumnFull(c)) {
                validColumns.add(c);
            }
        }

        if (validColumns.isEmpty()) {
            return -1;
        }

        return validColumns.get(random.nextInt(validColumns.size()));
    }
}