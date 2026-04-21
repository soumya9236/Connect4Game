package connect4.builder;

import connect4.controller.Connect4Game;
import connect4.player.Player;
import connect4.strategy.CleverStrategy;
import connect4.strategy.HumanStrategy;
import connect4.strategy.MasterStrategy;
import connect4.strategy.RandomStrategy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class Connect4GameBuilderTest {

    @Test
    void buildDefaultsCreatesStandardHumanVsHumanGame() {
        Connect4Game game = new Connect4GameBuilder().build();

        assertNotNull(game);
        assertEquals(6, game.getBoard().getRows());
        assertEquals(7, game.getBoard().getCols());

        Player player1 = game.getCurrentPlayer();
        assertEquals("Player 1", player1.getName());
        assertTrue(player1.getStrategy() instanceof HumanStrategy);

        game.makeMove(0);

        Player player2 = game.getCurrentPlayer();
        assertEquals("Player 2", player2.getName());
        assertTrue(player2.getStrategy() instanceof HumanStrategy);
    }

    @Test
    void withRowsAndWithColsChangeBoardSize() {
        Connect4Game game = new Connect4GameBuilder()
                .withRows(8)
                .withCols(9)
                .build();

        assertEquals(8, game.getBoard().getRows());
        assertEquals(9, game.getBoard().getCols());
    }

    @Test
    void withPlayerNamesChangesHumanVsHumanNames() {
        Connect4Game game = new Connect4GameBuilder()
                .withPlayerOneName("Shrim")
                .withPlayerTwoName("Aarya")
                .build();

        assertEquals("Shrim", game.getCurrentPlayer().getName());

        game.makeMove(0);

        assertEquals("Aarya", game.getCurrentPlayer().getName());
    }

    @Test
    void withModeHumanVsComputerAndEasyDifficultyCreatesRandomAi() {
        Connect4Game game = new Connect4GameBuilder()
                .withMode("hvc")
                .withDifficulty("easy")
                .build();

        Player player1 = game.getCurrentPlayer();
        assertEquals("Player 1", player1.getName());
        assertTrue(player1.getStrategy() instanceof HumanStrategy);

        game.makeMove(0);

        Player player2 = game.getCurrentPlayer();
        assertEquals("Computer (Easy)", player2.getName());
        assertTrue(player2.getStrategy() instanceof RandomStrategy);
    }

    @Test
    void withMediumDifficultyCreatesCleverStrategyAi() {
        Connect4Game game = new Connect4GameBuilder()
                .withMode("hvc")
                .withDifficulty("medium")
                .build();

        game.makeMove(0);

        Player player2 = game.getCurrentPlayer();
        assertEquals("Computer (Medium)", player2.getName());
        assertTrue(player2.getStrategy() instanceof CleverStrategy);
    }

    @Test
    void withHardDifficultyCreatesMasterStrategyAi() {
        Connect4Game game = new Connect4GameBuilder()
                .withMode("hvc")
                .withDifficulty("hard")
                .build();

        game.makeMove(0);

        Player player2 = game.getCurrentPlayer();
        assertEquals("Computer (Hard)", player2.getName());
        assertTrue(player2.getStrategy() instanceof MasterStrategy);
    }

    @Test
    void unknownDifficultyFallsBackToRandomStrategy() {
        Connect4Game game = new Connect4GameBuilder()
                .withMode("hvc")
                .withDifficulty("legendary")
                .build();

        game.makeMove(0);

        Player player2 = game.getCurrentPlayer();
        assertEquals("Computer (Legendary)", player2.getName());
        assertTrue(player2.getStrategy() instanceof RandomStrategy);
    }

    @Test
    void chainingAllWithMethodsStillBuildsCorrectly() {
        Connect4Game game = new Connect4GameBuilder()
                .withRows(5)
                .withCols(6)
                .withMode("hvc")
                .withDifficulty("medium")
                .withPlayerOneName("Alice")
                .withPlayerTwoName("Bob")
                .build();

        assertNotNull(game);
        assertEquals(5, game.getBoard().getRows());
        assertEquals(6, game.getBoard().getCols());

        Player player1 = game.getCurrentPlayer();
        assertEquals("Alice", player1.getName());

        game.makeMove(0);

        Player player2 = game.getCurrentPlayer();
        assertEquals("Computer (Medium)", player2.getName());
        assertTrue(player2.getStrategy() instanceof CleverStrategy);
    }

    @Test
    void capitalizePrivateMethodHandlesNormalString() throws Exception {
        Connect4GameBuilder builder = new Connect4GameBuilder();

        Method capitalize = Connect4GameBuilder.class.getDeclaredMethod("capitalize", String.class);
        capitalize.setAccessible(true);

        String result = (String) capitalize.invoke(builder, "hard");

        assertEquals("Hard", result);
    }

    @Test
    void capitalizePrivateMethodHandlesEmptyString() throws Exception {
        Connect4GameBuilder builder = new Connect4GameBuilder();

        Method capitalize = Connect4GameBuilder.class.getDeclaredMethod("capitalize", String.class);
        capitalize.setAccessible(true);

        String result = (String) capitalize.invoke(builder, "");

        assertEquals("", result);
    }

    @Test
    void capitalizePrivateMethodHandlesNull() throws Exception {
        Connect4GameBuilder builder = new Connect4GameBuilder();

        Method capitalize = Connect4GameBuilder.class.getDeclaredMethod("capitalize", String.class);
        capitalize.setAccessible(true);

        String result = (String) capitalize.invoke(builder, new Object[]{null});

        assertNull(result);
    }
}
