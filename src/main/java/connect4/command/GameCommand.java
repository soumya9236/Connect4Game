package connect4.command;

import connect4.controller.MoveResult;

public interface GameCommand {
    MoveResult execute();
    void undo();
}
