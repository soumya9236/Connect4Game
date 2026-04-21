package connect4.view;

import connect4.controller.Connect4Game;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.awt.event.MouseEvent;


import static org.junit.jupiter.api.Assertions.*;

public class GameSetupDialogTest {

    @Test
    void constructorBuildsDialog() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());

        GameSetupDialog dialog = new GameSetupDialog(null);
        assertNotNull(dialog.getContentPane());
        dialog.dispose();
    }

    @Test
    void capitalizeHandlesNormalEmptyAndNull() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());

        GameSetupDialog dialog = new GameSetupDialog(null);

        Method capitalize = GameSetupDialog.class.getDeclaredMethod("capitalize", String.class);
        capitalize.setAccessible(true);

        assertEquals("Hard", capitalize.invoke(dialog, "hard"));
        assertEquals("", capitalize.invoke(dialog, ""));
        assertNull(capitalize.invoke(dialog, new Object[]{null}));

        dialog.dispose();
    }

    @Test
    void onStartBuildsGame() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());

        GameSetupDialog dialog = new GameSetupDialog(null);

        Method onStart = GameSetupDialog.class.getDeclaredMethod("onStart");
        onStart.setAccessible(true);
        onStart.invoke(dialog);

        Field resultField = GameSetupDialog.class.getDeclaredField("result");
        resultField.setAccessible(true);

        Object result = resultField.get(dialog);
        assertNotNull(result);
        assertTrue(result instanceof Connect4Game);

        dialog.dispose();
    }

    @Test
    void getConfiguredGameReturnsNullIfDialogClosedWithoutStart() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());

        GameSetupDialog dialog = new GameSetupDialog(null);

        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
            dialog.dispose();
        }).start();

        Connect4Game game = dialog.getConfiguredGame();
        assertNull(game);
    }
    @Test
    void selectingDifferentOptionsStillBuildsGame() throws Exception {
        GameSetupDialog dialog = new GameSetupDialog(null);

        // simulate selecting HVC + hard difficulty
        Field modeGroupField = GameSetupDialog.class.getDeclaredField("modeGroup");
        modeGroupField.setAccessible(true);
        ButtonGroup modeGroup = (ButtonGroup) modeGroupField.get(dialog);

        for (AbstractButton btn : java.util.Collections.list(modeGroup.getElements())) {
            if (btn.getActionCommand().equals("hvc")) {
                btn.setSelected(true);
            }
        }

        Field diffGroupField = GameSetupDialog.class.getDeclaredField("diffGroup");
        diffGroupField.setAccessible(true);
        ButtonGroup diffGroup = (ButtonGroup) diffGroupField.get(dialog);

        for (AbstractButton btn : java.util.Collections.list(diffGroup.getElements())) {
            if (btn.getActionCommand().equals("hard")) {
                btn.setSelected(true);
            }
        }

        Method onStart = GameSetupDialog.class.getDeclaredMethod("onStart");
        onStart.setAccessible(true);
        onStart.invoke(dialog);

        Field resultField = GameSetupDialog.class.getDeclaredField("result");
        resultField.setAccessible(true);

        assertNotNull(resultField.get(dialog));

        dialog.dispose();
    }
    @Test
    void getSelectedReturnsNullWhenNothingIsSelected() throws Exception {
        GameSetupDialog dialog = new GameSetupDialog(null);

        Method getSelected = GameSetupDialog.class.getDeclaredMethod("getSelected", ButtonGroup.class);
        getSelected.setAccessible(true);

        ButtonGroup emptyGroup = new ButtonGroup();

        String result = (String) getSelected.invoke(dialog, emptyGroup);

        assertNull(result);

        dialog.dispose();
    }
    @Test
    void styledButtonMouseHoverChangesColor() throws Exception {
        GameSetupDialog dialog = new GameSetupDialog(null);

        Method styledButton = GameSetupDialog.class.getDeclaredMethod("styledButton", String.class);
        styledButton.setAccessible(true);

        JButton button = (JButton) styledButton.invoke(dialog, "Test Button");

        Color original = button.getBackground();

        MouseEvent enterEvent = new MouseEvent(
                button,
                MouseEvent.MOUSE_ENTERED,
                System.currentTimeMillis(),
                0,
                10,
                10,
                0,
                false
        );

        MouseEvent exitEvent = new MouseEvent(
                button,
                MouseEvent.MOUSE_EXITED,
                System.currentTimeMillis(),
                0,
                10,
                10,
                0,
                false
        );

        for (MouseListener listener : button.getMouseListeners()) {
            listener.mouseEntered(enterEvent);
        }

        assertNotEquals(original, button.getBackground());

        for (MouseListener listener : button.getMouseListeners()) {
            listener.mouseExited(exitEvent);
        }

        assertEquals(original, button.getBackground());

        dialog.dispose();
    }
}