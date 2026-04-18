package connect4;

import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.controller.MoveResult;
import connect4.factory.PieceFactory;
import connect4.factory.PlayerFactory;
import connect4.observer.ConsoleGameObserver;
import connect4.player.Player;
import connect4.strategy.HumanStrategy;
import connect4.strategy.RandomStrategy;
import connect4.strategy.StandardWinStrategy;
import connect4.view.BoardView;
import connect4.view.ConsoleBoardView;

public class Main {

    public static void main(String[] args) {
        GridBoard board = new GridBoard(6, 7);

        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();

        Player player1 = playerFactory.createPlayer(
                "Player 1",
                pieceFactory.createPiece("red"),
                new HumanStrategy()
        );

        Player player2 = playerFactory.createPlayer(
                "Player 2",
                pieceFactory.createPiece("blue"),
                new HumanStrategy()
                // or new RandomStrategy()
        );

        Connect4Game game =
                new Connect4Game(board, new Player[]{player1, player2}, new StandardWinStrategy());

        game.addObserver(new ConsoleGameObserver());

        BoardView view = new ConsoleBoardView();

        while (!game.isGameOver()) {
            view.display(game.getBoard());

            int column = game.getCurrentPlayer()
                    .getStrategy()
                    .selectColumn(game.getBoard(), game.getCurrentPlayer());

            MoveResult result = game.makeMove(column);

            if (result == MoveResult.INVALID) {
                System.out.println("Try again.");
            }
        }

        view.display(game.getBoard());
    }
}