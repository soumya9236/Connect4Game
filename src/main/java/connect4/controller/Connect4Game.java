package connect4.controller;

import connect4.board.Board;
import connect4.observer.GameObserver;
import connect4.player.Player;
import connect4.strategy.WinStrategy;

import java.util.ArrayList;
import java.util.List;

public class Connect4Game {

    private final Board board;
    private final Player[] players;
    private final WinStrategy winStrategy;
    private final List<GameObserver> observers = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;
    private Player winner = null;

    public Connect4Game(Board board, Player[] players, WinStrategy winStrategy) {
        this.board = board;
        this.players = players;
        this.winStrategy = winStrategy;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (GameObserver observer : observers) {
            observer.onGameMessage(message);
        }
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public Board getBoard() {
        return board;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public Player getWinner() {
        return winner;
    }


    public MoveResult makeMove(int column) {
        if (gameOver) {
            notifyObservers("The game is already over.");
            return MoveResult.GAME_OVER;
        }

        Player currentPlayer = getCurrentPlayer();
        boolean success = board.dropPiece(column, currentPlayer.getPiece().getSymbol());

        if (!success) {
            notifyObservers("Invalid move. Try again.");
            return MoveResult.INVALID;
        }

        notifyObservers(currentPlayer.getName() + " placed a piece in column " + column);

        boolean hasWon = winStrategy.checkWin(board, currentPlayer.getPiece().getSymbol());
        if (hasWon) {
            gameOver = true;
            winner = currentPlayer;
            notifyObservers(currentPlayer.getName() + " wins!");
            return MoveResult.WIN;
        }

        if (board.isFull()) {
            gameOver = true;
            notifyObservers("The game is a draw.");
            return MoveResult.DRAW;
        }

        switchTurn();
        return MoveResult.SUCCESS;
    }




    public void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        notifyObservers("It is now " + getCurrentPlayer().getName() + "'s turn.");
    }
}