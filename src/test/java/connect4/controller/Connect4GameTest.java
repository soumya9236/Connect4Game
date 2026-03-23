package connect4.controller;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.piece.BluePiece;
import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import connect4.strategy.StandardWinStrategy;
import connect4.strategy.WinStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Connect4GameTest {

    @Test
    void currentPlayerStartsAsPlayerOne() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece());
        Player p2 = new HumanPlayer("Player 2", new BluePiece());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    void switchTurnChangesCurrentPlayer() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece());
        Player p2 = new HumanPlayer("Player 2", new BluePiece());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        game.switchTurn();

        assertEquals("Player 2", game.getCurrentPlayer().getName());
    }

    @Test
    void validMoveReturnsTrue() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece());
        Player p2 = new HumanPlayer("Player 2", new BluePiece());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        assertTrue(game.makeMove(0));
    }

    @Test
    void getBoardReturnsBoardPassedIn() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece());
        Player p2 = new HumanPlayer("Player 2", new BluePiece());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        assertEquals(board, game.getBoard());
    }
}