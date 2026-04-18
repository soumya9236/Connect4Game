package connect4.strategy;

import connect4.board.Board;
import connect4.player.Player;

import java.util.Scanner;

public class HumanStrategy implements MoveStrategy {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int selectColumn(Board board, Player player) {
        System.out.print(player.getName() + ", choose a column: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print(player.getName() + ", choose a column: ");
        }
        return scanner.nextInt();
    }
}