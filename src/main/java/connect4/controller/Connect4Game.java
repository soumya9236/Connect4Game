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

    public boolean makeMove(int column) {
        Player currentPlayer = getCurrentPlayer();
        boolean success = board.dropPiece(column, currentPlayer.getPiece().getSymbol());

        if (!success) {
            notifyObservers("Invalid move. Try again.");
            return false;
        }

        notifyObservers(currentPlayer.getName() + " placed a piece in column " + column);
        return true;
    }

    public boolean checkWinner() {
        boolean winner = winStrategy.checkWin(board, getCurrentPlayer().getPiece().getSymbol());

        if (winner) {
            notifyObservers(getCurrentPlayer().getName() + " wins!");
        }

        return winner;
    }

    public boolean isDraw() {
        boolean draw = board.isFull();

        if (draw) {
            notifyObservers("The game is a draw.");
        }

        return draw;
    }

    public void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        notifyObservers("It is now " + getCurrentPlayer().getName() + "'s turn.");
    }
}