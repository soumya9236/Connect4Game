package connect4.builder;

import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.factory.PieceFactory;
import connect4.factory.PlayerFactory;
import connect4.player.Player;
import connect4.strategy.*;
import connect4.strategy.HardStrategy;

public class Connect4GameBuilder {

    private int rows = 6;
    private int cols = 7;

    private String mode = "hvh";
    private String difficulty = "easy";

    private String playerOneName = "Player 1";
    private String playerTwoName = "Player 2";

    public Connect4GameBuilder withRows(int rows) {
        this.rows = rows;
        return this;
    }

    public Connect4GameBuilder withCols(int cols) {
        this.cols = cols;
        return this;
    }

    public Connect4GameBuilder withMode(String mode) {
        this.mode = mode;
        return this;
    }

    public Connect4GameBuilder withDifficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public Connect4GameBuilder withPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
        return this;
    }

    public Connect4GameBuilder withPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
        return this;
    }

    public Connect4Game build() {
        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        GridBoard board = new GridBoard(rows, cols);

        Player player1 = playerFactory.createPlayer(
                playerOneName,
                pieceFactory.createPiece("red"),
                new HumanStrategy()
        );

        Player player2;
        if ("hvc".equals(mode)) {
            MoveStrategy aiStrategy = switch (difficulty) {
                case "medium" -> new MediumStrategy();
                case "hard" -> new HardStrategy();
                default -> new EasyStrategy();
            };

            player2 = playerFactory.createPlayer(
                    "Computer (" + capitalize(difficulty) + ")",
                    pieceFactory.createPiece("blue"),
                    aiStrategy
            );
        } else {
            player2 = playerFactory.createPlayer(
                    playerTwoName,
                    pieceFactory.createPiece("blue"),
                    new HumanStrategy()
            );
        }

        return new Connect4Game(
                board,
                new Player[]{player1, player2},
                new StandardWinStrategy()
        );
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}