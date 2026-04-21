package connect4.view;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class DiscCellTest {

    @Test
    void constructorSetsPreferredSizeAndOpaqueFalse() {
        DiscCell cell = new DiscCell();

        assertEquals(new Dimension(60, 60), cell.getPreferredSize());
        assertFalse(cell.isOpaque());
    }

    @Test
    void setValueDoesNotThrowForRed() {
        DiscCell cell = new DiscCell();
        assertDoesNotThrow(() -> cell.setValue('R'));
    }

    @Test
    void setValueDoesNotThrowForBlue() {
        DiscCell cell = new DiscCell();
        assertDoesNotThrow(() -> cell.setValue('B'));
    }

    @Test
    void setValueDoesNotThrowForEmpty() {
        DiscCell cell = new DiscCell();
        assertDoesNotThrow(() -> cell.setValue('.'));
    }

    @Test
    void paintComponentWorksForRedBlueAndEmpty() {
        DiscCell cell = new DiscCell();
        cell.setSize(60, 60);

        BufferedImage image = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        assertDoesNotThrow(() -> {
            cell.setValue('R');
            cell.paint(g2);

            cell.setValue('B');
            cell.paint(g2);

            cell.setValue('.');
            cell.paint(g2);
        });

        g2.dispose();
    }
}
