package connect4.view;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.factory.PieceFactory;
import connect4.factory.PlayerFactory;
import connect4.player.Player;
import connect4.strategy.EasyStrategy;
import connect4.strategy.HumanStrategy;
import connect4.strategy.StandardWinStrategy;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class SwingConnect4Test {

    private Connect4Game makeHumanVsHumanGame() {
        GridBoard board = new GridBoard(6, 7);
        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        Player p1 = playerFactory.createPlayer("Player 1", pieceFactory.createPiece("red"), new HumanStrategy());
        Player p2 = playerFactory.createPlayer("Player 2", pieceFactory.createPiece("blue"), new HumanStrategy());
        return new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
    }

    private Connect4Game makeHumanVsAiGame() {
        GridBoard board = new GridBoard(6, 7);
        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        Player p1 = playerFactory.createPlayer("Player 1", pieceFactory.createPiece("red"), new HumanStrategy());
        Player p2 = playerFactory.createPlayer("Computer", pieceFactory.createPiece("blue"), new EasyStrategy());
        return new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
    }

    private Connect4Game makeAiVsHumanGame() {
        GridBoard board = new GridBoard(6, 7);
        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        Player p1 = playerFactory.createPlayer("Computer", pieceFactory.createPiece("red"), new EasyStrategy());
        Player p2 = playerFactory.createPlayer("Player 2", pieceFactory.createPiece("blue"), new HumanStrategy());
        return new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
    }

    @BeforeEach
    void requireGui() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
    }

    // ── existing tests ────────────────────────────────────────────────────────

    @Test
    void constructorBuildsUiAndDisplayWorks() {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        assertDoesNotThrow(() -> view.display(viewGame(view).getBoard()));
        assertNotNull(getFrame(view));
        getFrame(view).dispose();
    }

    @Test
    void showDoesNotThrow() {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        assertDoesNotThrow(view::show);
        getFrame(view).dispose();
    }

    @Test
    void updateMessageSetsHumanTurnText() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invoke(view, "updateMessage");
        assertTrue(getMessageLabel(view).getText().contains("Player 1"));
        getFrame(view).dispose();
    }

    @Test
    void disableColumnButtonsDisablesAllButtons() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invoke(view, "disableColumnButtons");
        for (JButton button : getButtons(view)) assertFalse(button.isEnabled());
        getFrame(view).dispose();
    }

    @Test
    void handleMoveProcessesHumanMove() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invokeWithInt(view, "handleMove", 0);
        assertEquals('R', viewGame(view).getBoard().getCell(5, 0));
        getFrame(view).dispose();
    }

    @Test
    void handleMoveIgnoresClickWhenGameOver() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        Connect4Game game = viewGame(view);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0);
        assertDoesNotThrow(() -> invokeWithInt(view, "handleMove", 2));
        getFrame(view).dispose();
    }

    @Test
    void handleMoveInvalidColumnShowsErrorMessage() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invokeWithInt(view, "handleMove", -1);
        assertTrue(getMessageLabel(view).getText().toLowerCase().contains("invalid"));
        getFrame(view).dispose();
    }

    @Test
    void handleMoveIgnoresClickWhenItIsAiTurn() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsAiGame());
        Connect4Game game = viewGame(view);
        game.makeMove(0);
        assertDoesNotThrow(() -> invokeWithInt(view, "handleMove", 1));
        assertEquals('.', game.getBoard().getCell(5, 1));
        getFrame(view).dispose();
    }

    @Test
    void maybePlayAiTurnRunsForAiPlayer() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsAiGame());
        Connect4Game game = viewGame(view);
        game.makeMove(0);
        invoke(view, "maybePlayAiTurn");
        Thread.sleep(700);
        Board board = game.getBoard();
        boolean foundBlue = false;
        for (int r = 0; r < board.getRows(); r++)
            for (int c = 0; c < board.getCols(); c++)
                if (board.getCell(r, c) == 'B') foundBlue = true;
        assertTrue(foundBlue);
        getFrame(view).dispose();
    }

    @Test
    void maybePlayAiTurnDoesNothingIfGameOver() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        Connect4Game game = viewGame(view);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0);
        assertDoesNotThrow(() -> invoke(view, "maybePlayAiTurn"));
        getFrame(view).dispose();
    }

    @Test
    void maybePlayAiTurnDoesNothingOnHumanTurn() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        assertDoesNotThrow(() -> invoke(view, "maybePlayAiTurn"));
        getFrame(view).dispose();
    }

    @Test
    void constructorTriggersImmediateAiMoveWhenPlayerOneIsAi() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeAiVsHumanGame());
        Thread.sleep(700);
        Board board = viewGame(view).getBoard();
        boolean foundRed = false;
        for (int r = 0; r < board.getRows(); r++)
            for (int c = 0; c < board.getCols(); c++)
                if (board.getCell(r, c) == 'R') foundRed = true;
        assertTrue(foundRed);
        getFrame(view).dispose();
    }

    @Test
    void updateMessageSetsAiThinkingText() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsAiGame());
        viewGame(view).makeMove(0);
        invoke(view, "updateMessage");
        assertTrue(getMessageLabel(view).getText().contains("thinking"));
        getFrame(view).dispose();
    }

    @Test
    void displayUpdatesDiscCellsWithoutThrowing() {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        Connect4Game game = viewGame(view);
        game.makeMove(0); game.makeMove(1);
        assertDoesNotThrow(() -> view.display(game.getBoard()));
        getFrame(view).dispose();
    }

    @Test
    void exitIfNoGameDoesNothingWhenGameNotNull() {
        assertDoesNotThrow(() -> SwingConnect4.exitIfNoGame(makeHumanVsHumanGame()));
    }

    // ── undo tests ────────────────────────────────────────────────────────────

    @Test
    void undoButtonExistsInUi() {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        JButton undoButton = getUndoButton(view);
        assertNotNull(undoButton);
        getFrame(view).dispose();
    }

    @Test
    void undoButtonEnabledAfterMove() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invokeWithInt(view, "handleMove", 0);
        assertTrue(getUndoButton(view).isEnabled());
        getFrame(view).dispose();
    }

    @Test
    void handleUndoRestoresBoardAfterMove() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invokeWithInt(view, "handleMove", 0);
        assertEquals('R', viewGame(view).getBoard().getCell(5, 0));
        invoke(view, "handleUndo");
        assertEquals('.', viewGame(view).getBoard().getCell(5, 0));
        getFrame(view).dispose();
    }

    @Test
    void handleUndoRestoresCurrentPlayer() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invokeWithInt(view, "handleMove", 0);
        assertEquals("Player 2", viewGame(view).getCurrentPlayer().getName());
        invoke(view, "handleUndo");
        assertEquals("Player 1", viewGame(view).getCurrentPlayer().getName());
        getFrame(view).dispose();
    }

    @Test
    void handleUndoWhenNothingToUndoShowsMessage() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invoke(view, "handleUndo");
        assertTrue(getMessageLabel(view).getText().toLowerCase().contains("nothing"));
        getFrame(view).dispose();
    }

    @Test
    void handleUndoRestoresBoardWhenHistoryHasOneMove() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        invokeWithInt(view, "handleMove", 0);
        invoke(view, "handleUndo");
        assertEquals('.', viewGame(view).getBoard().getCell(5, 0));
        getFrame(view).dispose();
    }

    @Test
    void handleUndoWhenNoUndosLeftDoesNotCrash() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());

        Field redUndosField = SwingConnect4.class.getDeclaredField("redUndosLeft");
        redUndosField.setAccessible(true);
        redUndosField.set(view, 0);

        invokeWithInt(view, "handleMove", 0);
        assertDoesNotThrow(() -> invoke(view, "handleUndo"));
        getFrame(view).dispose();
    }

    // ── testable subclass tests ───────────────────────────────────────────────

    static class TestableSwingConnect4 extends SwingConnect4 {
        private final int dialogChoice;
        boolean exitCalled = false;

        TestableSwingConnect4(Connect4Game game, int dialogChoice) {
            super(game);
            this.dialogChoice = dialogChoice;
        }

        @Override
        protected int showEndGameDialog(String messageText, String title) {
            return dialogChoice;
        }

        @Override
        protected void exitApplication() {
            exitCalled = true;
        }
    }

    @Test
    void showGameOverWinnerExitBranchDisablesButtonsSetsMessageAndCallsExit() throws Exception {
        TestableSwingConnect4 view = new TestableSwingConnect4(makeHumanVsHumanGame(), 1);
        Connect4Game game = viewGame(view);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0);
        Method showGameOver = SwingConnect4.class.getDeclaredMethod("showGameOver");
        showGameOver.setAccessible(true);
        showGameOver.invoke(view);
        assertTrue(getMessageLabel(view).getText().contains("wins"));
        for (JButton button : getButtons(view)) assertFalse(button.isEnabled());
        assertTrue(view.exitCalled);
        getFrame(view).dispose();
    }

    @Test
    void showGameOverDrawExitBranchSetsDrawMessageAndCallsExit() throws Exception {
        GridBoard board = new GridBoard(2, 2);
        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        Player p1 = playerFactory.createPlayer("Player 1", pieceFactory.createPiece("red"), new HumanStrategy());
        Player p2 = playerFactory.createPlayer("Player 2", pieceFactory.createPiece("blue"), new HumanStrategy());
        Connect4Game game = new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
        TestableSwingConnect4 view = new TestableSwingConnect4(game, 1);
        game.makeMove(0); game.makeMove(1);
        game.makeMove(0); game.makeMove(1);
        Method showGameOver = SwingConnect4.class.getDeclaredMethod("showGameOver");
        showGameOver.setAccessible(true);
        showGameOver.invoke(view);
        assertTrue(getMessageLabel(view).getText().toLowerCase().contains("draw"));
        assertTrue(view.exitCalled);
        getFrame(view).dispose();
    }

    @Test
    void showEndGameDialogOverrideReturnsConfiguredChoice() {
        TestableSwingConnect4 view = new TestableSwingConnect4(makeHumanVsHumanGame(), 0);
        assertEquals(0, view.showEndGameDialog("Test", "Title"));
        getFrame(view).dispose();
    }

    @Test
    void exitApplicationOverrideSetsFlag() {
        TestableSwingConnect4 view = new TestableSwingConnect4(makeHumanVsHumanGame(), 1);
        view.exitApplication();
        assertTrue(view.exitCalled);
        getFrame(view).dispose();
    }

    @Test
    void showEndGameDialogReturnsChoiceFromOverride() {
        TestableSwingConnect4 view = new TestableSwingConnect4(makeHumanVsHumanGame(), 1);
        assertEquals(1, view.showEndGameDialog("Test", "Title"));
        getFrame(view).dispose();
    }

    @Test
    void exitApplicationSetsFlagInOverride() {
        TestableSwingConnect4 view = new TestableSwingConnect4(makeHumanVsHumanGame(), 0);
        view.exitApplication();
        assertTrue(view.exitCalled);
        getFrame(view).dispose();
    }

    // ── reflection helpers ────────────────────────────────────────────────────

    private void invoke(SwingConnect4 view, String methodName) throws Exception {
        Method m = SwingConnect4.class.getDeclaredMethod(methodName);
        m.setAccessible(true);
        m.invoke(view);
    }

    private void invokeWithInt(SwingConnect4 view, String methodName, int arg) throws Exception {
        Method m = SwingConnect4.class.getDeclaredMethod(methodName, int.class);
        m.setAccessible(true);
        m.invoke(view, arg);
    }

    private JFrame getFrame(SwingConnect4 view) {
        try {
            Field f = SwingConnect4.class.getDeclaredField("frame");
            f.setAccessible(true);
            return (JFrame) f.get(view);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private JLabel getMessageLabel(SwingConnect4 view) {
        try {
            Field f = SwingConnect4.class.getDeclaredField("message");
            f.setAccessible(true);
            return (JLabel) f.get(view);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private JButton[] getButtons(SwingConnect4 view) {
        try {
            Field f = SwingConnect4.class.getDeclaredField("columnButtons");
            f.setAccessible(true);
            return (JButton[]) f.get(view);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private JButton getUndoButton(SwingConnect4 view) {
        try {
            Field f = SwingConnect4.class.getDeclaredField("undoButton");
            f.setAccessible(true);
            return (JButton) f.get(view);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private Connect4Game viewGame(SwingConnect4 view) {
        try {
            Field f = SwingConnect4.class.getDeclaredField("game");
            f.setAccessible(true);
            return (Connect4Game) f.get(view);
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}