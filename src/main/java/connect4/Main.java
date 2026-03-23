package connect4;

import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.factory.PieceFactory;
import connect4.factory.PlayerFactory;
import connect4.observer.ConsoleGameObserver;
import connect4.player.Player;
import connect4.strategy.StandardWinStrategy;
import connect4.view.ConsoleBoardView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GridBoard board = new GridBoard(6, 7);

        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();

        Player player1 = playerFactory.createPlayer("Player 1", pieceFactory.createPiece("red"));
        Player player2 = playerFactory.createPlayer("Player 2", pieceFactory.createPiece("blue"));

        Connect4Game game =
                new Connect4Game(board, new Player[]{player1, player2}, new StandardWinStrategy());

        game.addObserver(new ConsoleGameObserver());

        ConsoleBoardView view = new ConsoleBoardView();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            view.display(game.getBoard());

            System.out.print(game.getCurrentPlayer().getName() + ", choose a column: ");
            int column = scanner.nextInt();

            if (!game.makeMove(column)) {
                continue;
            }

            if (game.checkWinner()) {
                view.display(game.getBoard());
                break;
            }

            if (game.isDraw()) {
                view.display(game.getBoard());
                break;
            }

            game.switchTurn();
        }

        scanner.close();
    }
}