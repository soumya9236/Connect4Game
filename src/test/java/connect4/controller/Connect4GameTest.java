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
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    void validMoveSwitchesTurnToPlayerTwo() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        game.makeMove(0);
        assertEquals("Player 2", game.getCurrentPlayer().getName());
    }

    @Test
    void validMoveReturnsSuccess() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        assertEquals(MoveResult.SUCCESS, game.makeMove(0));
        assertEquals('R', board.getCell(5, 0));
    }

    @Test
    void getBoardReturnsBoardPassedIn() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        assertEquals(board, game.getBoard());
    }

    @Test
    void invalidMoveReturnsInvalid() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        assertEquals(MoveResult.INVALID, game.makeMove(-1));
    }

    @Test
    void invalidMoveDoesNotSwitchTurn() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        game.makeMove(-1);
        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    void winningMoveSetsGameOverAndWinner() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        MoveResult result = game.makeMove(0);
        assertEquals(MoveResult.WIN, result);
        assertTrue(game.isGameOver());
        assertEquals("Player 1", game.getWinner().getName());
    }

    @Test
    void fullBoardResultsInDraw() {
        Board board = new GridBoard(2, 2);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0);
        MoveResult result = game.makeMove(1);
        assertEquals(MoveResult.DRAW, result);
        assertTrue(game.isGameOver());
        assertNull(game.getWinner());
    }

    @Test
    void moveAfterGameOverReturnsGameOver() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0);
        assertEquals(MoveResult.GAME_OVER, game.makeMove(2));
    }

    @Test
    void observerReceivesMessages() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        final boolean[] called = {false};
        game.addObserver(message -> called[0] = true);
        game.makeMove(0);
        assertTrue(called[0]);
    }

    @Test
    void switchTurnMethodWorks() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        game.switchTurn();
        assertEquals("Player 2", game.getCurrentPlayer().getName());
    }

    @Test
    void saveStateCaptursBoardAndPlayerIndex() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());

        game.makeMove(0); // Player 1 moves, now Player 2's turn

        Connect4Game.GameState state = game.saveState();
        assertNotNull(state);
    }

    @Test
    void restoreStateRestoresBoardAndPlayer() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());

        Connect4Game.GameState stateBefore = game.saveState();

        game.makeMove(0); // Player 1 moves
        assertEquals('R', board.getCell(5, 0));
        assertEquals("Player 2", game.getCurrentPlayer().getName());

        game.restoreState(stateBefore);

        assertEquals('.', board.getCell(5, 0));
        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    void restoreStateRestoresGameOverFlag() {
        Board board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());

        Connect4Game.GameState stateBefore = game.saveState();

        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); // win

        assertTrue(game.isGameOver());

        game.restoreState(stateBefore);

        assertFalse(game.isGameOver());
        assertNull(game.getWinner());
    }
}