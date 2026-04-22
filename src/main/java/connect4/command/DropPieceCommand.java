package connect4.command;

import connect4.controller.Connect4Game;
import connect4.controller.MoveResult;

public class DropPieceCommand implements GameCommand {
    private final Connect4Game game;
    private final int column;

    private Connect4Game.GameState previousState;
    private boolean executedSuccessfully = false;

    public DropPieceCommand(Connect4Game game, int column) {
        this.game = game;
        this.column = column;
    }

    @Override
    public MoveResult execute() {
        previousState = game.saveState();
        MoveResult result = game.makeMove(column);

        executedSuccessfully = result != MoveResult.INVALID && result != MoveResult.GAME_OVER;
        return result;
    }

    @Override
    public void undo() {
        if (executedSuccessfully && previousState != null) {
            game.restoreState(previousState);
        }
    }
}