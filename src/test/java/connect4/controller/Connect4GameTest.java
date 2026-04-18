package connect4.controller;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.piece.BluePiece;
import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import connect4.strategy.HumanStrategy;
import connect4.strategy.StandardWinStrategy;
import connect4.strategy.WinStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Connect4GameTest {

    @Test
    void currentPlayerStartsAsPlayerOne() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(),new HumanStrategy());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    void validMoveSwitchesTurnToPlayerTwo() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(),new HumanStrategy());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        game.makeMove(0);

        assertEquals("Player 2", game.getCurrentPlayer().getName());
    }

    @Test
    void validMoveReturnsSuccess() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(),new HumanStrategy());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        assertEquals(MoveResult.SUCCESS, game.makeMove(0));
        assertEquals('R', board.getCell(5, 0));
    }

    @Test
    void getBoardReturnsBoardPassedIn() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(),new HumanStrategy());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        assertEquals(board, game.getBoard());
    }
    @Test
    void invalidMoveReturnsInvalid() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(),new HumanStrategy());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        assertEquals(MoveResult.INVALID, game.makeMove(-1));
    }
    @Test
    void invalidMoveDoesNotSwitchTurn() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(),new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(),new HumanStrategy());
        WinStrategy strategy = new StandardWinStrategy();

        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, strategy);

        game.makeMove(-1);

        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

}