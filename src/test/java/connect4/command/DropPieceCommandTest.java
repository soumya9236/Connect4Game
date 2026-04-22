package connect4.command;

import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.controller.MoveResult;
import connect4.piece.BluePiece;
import connect4.piece.RedPiece;
import connect4.player.HumanPlayer;
import connect4.player.Player;
import connect4.strategy.HumanStrategy;
import connect4.strategy.StandardWinStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropPieceCommandTest {

    private Connect4Game game;

    @BeforeEach
    void setUp() {
        GridBoard board = new GridBoard(6, 7);
        Player p1 = new HumanPlayer("Player 1", new RedPiece(), new HumanStrategy());
        Player p2 = new HumanPlayer("Player 2", new BluePiece(), new HumanStrategy());
        game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
    }

    @Test
    void executeDropsPieceInCorrectColumn() {
        DropPieceCommand command = new DropPieceCommand(game, 3);
        command.execute();

        assertEquals('R', game.getBoard().getCell(5, 3));
    }

    @Test
    void executeReturnsSuccessForValidMove() {
        DropPieceCommand command = new DropPieceCommand(game, 0);
        MoveResult result = command.execute();

        assertEquals(MoveResult.SUCCESS, result);
    }

    @Test
    void executeReturnsInvalidForBadColumn() {
        DropPieceCommand command = new DropPieceCommand(game, -1);
        MoveResult result = command.execute();

        assertEquals(MoveResult.INVALID, result);
    }

    @Test
    void undoRestoresBoardToPreviousState() {
        DropPieceCommand command = new DropPieceCommand(game, 3);
        command.execute();

        assertEquals('R', game.getBoard().getCell(5, 3));

        command.undo();

        assertEquals('.', game.getBoard().getCell(5, 3));
    }

    @Test
    void undoRestoresCurrentPlayerToPrevious() {
        assertEquals("Player 1", game.getCurrentPlayer().getName());

        DropPieceCommand command = new DropPieceCommand(game, 0);
        command.execute();

        assertEquals("Player 2", game.getCurrentPlayer().getName());

        command.undo();

        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    void undoDoesNothingIfExecuteWasInvalid() {
        DropPieceCommand command = new DropPieceCommand(game, -1);
        command.execute();

        // board should still be empty
        command.undo();

        assertEquals('.', game.getBoard().getCell(5, 0));
    }

    @Test
    void undoDoesNothingIfExecuteWasNeverCalled() {
        DropPieceCommand command = new DropPieceCommand(game, 0);

        assertDoesNotThrow(command::undo);

        assertEquals('.', game.getBoard().getCell(5, 0));
    }

    @Test
    void multipleCommandsCanBeUndoneInOrder() {
        DropPieceCommand cmd1 = new DropPieceCommand(game, 0);
        cmd1.execute();

        DropPieceCommand cmd2 = new DropPieceCommand(game, 1);
        cmd2.execute();

        assertEquals('R', game.getBoard().getCell(5, 0));
        assertEquals('B', game.getBoard().getCell(5, 1));

        cmd2.undo();
        assertEquals('.', game.getBoard().getCell(5, 1));

        cmd1.undo();
        assertEquals('.', game.getBoard().getCell(5, 0));
    }

    @Test
    void executeAfterWinReturnsGameOver() {
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);

        DropPieceCommand command = new DropPieceCommand(game, 2);
        MoveResult result = command.execute();

        assertEquals(MoveResult.GAME_OVER, result);
    }
}