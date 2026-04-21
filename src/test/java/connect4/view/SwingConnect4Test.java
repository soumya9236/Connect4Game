package connect4.view;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.factory.PieceFactory;
import connect4.factory.PlayerFactory;
import connect4.player.Player;
import connect4.strategy.HumanStrategy;
import connect4.strategy.RandomStrategy;
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
        Player p2 = playerFactory.createPlayer("Computer", pieceFactory.createPiece("blue"), new RandomStrategy());

        return new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
    }

    @BeforeEach
    void requireGui() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
    }

    @Test
    void constructorBuildsUiAndDisplayWorks() {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());

        assertDoesNotThrow(() -> view.display(viewGame(view).getBoard()));

        JFrame frame = getFrame(view);
        assertNotNull(frame);
        frame.dispose();
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

        Method updateMessage = SwingConnect4.class.getDeclaredMethod("updateMessage");
        updateMessage.setAccessible(true);
        updateMessage.invoke(view);

        JLabel message = getMessageLabel(view);
        assertTrue(message.getText().contains("Player 1"));

        getFrame(view).dispose();
    }

    @Test
    void disableColumnButtonsDisablesAllButtons() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());

        Method disableButtons = SwingConnect4.class.getDeclaredMethod("disableColumnButtons");
        disableButtons.setAccessible(true);
        disableButtons.invoke(view);

        JButton[] buttons = getButtons(view);
        for (JButton button : buttons) {
            assertFalse(button.isEnabled());
        }

        getFrame(view).dispose();
    }

    @Test
    void handleMoveProcessesHumanMove() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());

        Method handleMove = SwingConnect4.class.getDeclaredMethod("handleMove", int.class);
        handleMove.setAccessible(true);
        handleMove.invoke(view, 0);

        Connect4Game game = viewGame(view);
        assertEquals('R', game.getBoard().getCell(5, 0));

        getFrame(view).dispose();
    }

    @Test
    void handleMoveIgnoresClickWhenGameOver() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        Connect4Game game = viewGame(view);

        // force win for player 1
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);

        Method handleMove = SwingConnect4.class.getDeclaredMethod("handleMove", int.class);
        handleMove.setAccessible(true);

        assertDoesNotThrow(() -> handleMove.invoke(view, 2));

        getFrame(view).dispose();
    }

    @Test
    void maybePlayAiTurnRunsForAiPlayer() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsAiGame());
        Connect4Game game = viewGame(view);

        // human move first, now AI turn
        game.makeMove(0);

        Method maybePlayAiTurn = SwingConnect4.class.getDeclaredMethod("maybePlayAiTurn");
        maybePlayAiTurn.setAccessible(true);
        maybePlayAiTurn.invoke(view);

        // timer waits 400 ms, give it a little time
        Thread.sleep(700);

        Board board = game.getBoard();
        boolean foundBlue = false;
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                if (board.getCell(r, c) == 'B') {
                    foundBlue = true;
                }
            }
        }

        assertTrue(foundBlue);

        getFrame(view).dispose();
    }

    @Test
    void displayUpdatesDiscCellsWithoutThrowing() {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        Connect4Game game = viewGame(view);

        game.makeMove(0);
        game.makeMove(1);

        assertDoesNotThrow(() -> view.display(game.getBoard()));

        getFrame(view).dispose();
    }

    private JFrame getFrame(SwingConnect4 view) {
        try {
            Field field = SwingConnect4.class.getDeclaredField("frame");
            field.setAccessible(true);
            return (JFrame) field.get(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel getMessageLabel(SwingConnect4 view) {
        try {
            Field field = SwingConnect4.class.getDeclaredField("message");
            field.setAccessible(true);
            return (JLabel) field.get(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private JButton[] getButtons(SwingConnect4 view) {
        try {
            Field field = SwingConnect4.class.getDeclaredField("columnButtons");
            field.setAccessible(true);
            return (JButton[]) field.get(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Connect4Game viewGame(SwingConnect4 view) {
        try {
            Field field = SwingConnect4.class.getDeclaredField("game");
            field.setAccessible(true);
            return (Connect4Game) field.get(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void handleMoveInvalidColumnShowsErrorMessage() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());

        Method handleMove = SwingConnect4.class.getDeclaredMethod("handleMove", int.class);
        handleMove.setAccessible(true);

        // invalid column
        handleMove.invoke(view, -1);

        JLabel message = getMessageLabel(view);
        assertTrue(message.getText().toLowerCase().contains("invalid"));

        getFrame(view).dispose();
    }
    @Test
    void maybePlayAiTurnDoesNothingIfGameOver() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());
        Connect4Game game = viewGame(view);

        // force win
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);

        Method maybePlayAiTurn = SwingConnect4.class.getDeclaredMethod("maybePlayAiTurn");
        maybePlayAiTurn.setAccessible(true);

        assertDoesNotThrow(() -> maybePlayAiTurn.invoke(view));

        getFrame(view).dispose();
    }
    @Test
    void handleMoveIgnoresClickWhenItIsAiTurn() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsAiGame());
        Connect4Game game = viewGame(view);


        game.makeMove(0);

        Board before = game.getBoard();

        Method handleMove = SwingConnect4.class.getDeclaredMethod("handleMove", int.class);
        handleMove.setAccessible(true);

        assertDoesNotThrow(() -> handleMove.invoke(view, 1));


        assertEquals('.', before.getCell(5, 1));

        getFrame(view).dispose();
    }
    @Test
    void updateMessageSetsAiThinkingText() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsAiGame());
        Connect4Game game = viewGame(view);

        // make it AI turn
        game.makeMove(0);

        Method updateMessage = SwingConnect4.class.getDeclaredMethod("updateMessage");
        updateMessage.setAccessible(true);
        updateMessage.invoke(view);

        JLabel message = getMessageLabel(view);
        assertTrue(message.getText().contains("thinking"));

        getFrame(view).dispose();
    }
    @Test
    void maybePlayAiTurnDoesNothingOnHumanTurn() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeHumanVsHumanGame());

        Method maybePlayAiTurn = SwingConnect4.class.getDeclaredMethod("maybePlayAiTurn");
        maybePlayAiTurn.setAccessible(true);

        assertDoesNotThrow(() -> maybePlayAiTurn.invoke(view));

        getFrame(view).dispose();
    }
    @Test
    void constructorTriggersImmediateAiMoveWhenPlayerOneIsAi() throws Exception {
        SwingConnect4 view = new SwingConnect4(makeAiVsHumanGame());
        Connect4Game game = viewGame(view);

        Thread.sleep(700);

        boolean foundRed = false;
        Board board = game.getBoard();

        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                if (board.getCell(r, c) == 'R') {
                    foundRed = true;
                }
            }
        }

        assertTrue(foundRed);

        getFrame(view).dispose();
    }
    private Connect4Game makeAiVsHumanGame() {
        GridBoard board = new GridBoard(6, 7);
        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();

        Player p1 = playerFactory.createPlayer("Computer", pieceFactory.createPiece("red"), new RandomStrategy());
        Player p2 = playerFactory.createPlayer("Player 2", pieceFactory.createPiece("blue"), new HumanStrategy());

        return new Connect4Game(board, new Player[]{p1, p2}, new StandardWinStrategy());
    }
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

        // force a win for Player 1
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);

        Method showGameOver = SwingConnect4.class.getDeclaredMethod("showGameOver");
        showGameOver.setAccessible(true);
        showGameOver.invoke(view);

        JLabel message = getMessageLabel(view);
        assertTrue(message.getText().contains("wins"));

        JButton[] buttons = getButtons(view);
        for (JButton button : buttons) {
            assertFalse(button.isEnabled());
        }

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

        game.makeMove(0);
        game.makeMove(1);
        game.makeMove(0);
        game.makeMove(1); // draw

        Method showGameOver = SwingConnect4.class.getDeclaredMethod("showGameOver");
        showGameOver.setAccessible(true);
        showGameOver.invoke(view);

        JLabel message = getMessageLabel(view);
        assertTrue(message.getText().toLowerCase().contains("draw"));

        assertTrue(view.exitCalled);

        getFrame(view).dispose();
    }
    @Test
    void showEndGameDialogOverrideReturnsConfiguredChoice() {
        TestableSwingConnect4 view = new TestableSwingConnect4(makeHumanVsHumanGame(), 0);

        int choice = view.showEndGameDialog("Test", "Title");

        assertEquals(0, choice);

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

        int result = view.showEndGameDialog("Test", "Title");

        assertEquals(1, result);

        getFrame(view).dispose();
    }
    @Test
    void exitApplicationSetsFlagInOverride() {
        TestableSwingConnect4 view = new TestableSwingConnect4(makeHumanVsHumanGame(), 0);

        view.exitApplication();

        assertTrue(view.exitCalled);

        getFrame(view).dispose();
    }
    @Test
    void exitIfNoGameDoesNothingWhenGameNotNull() {
        assertDoesNotThrow(() ->
                SwingConnect4.exitIfNoGame(makeHumanVsHumanGame())
        );
    }


}