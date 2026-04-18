package connect4.view;

import javax.swing.*;
import java.awt.*;

public class DiscCell extends JPanel {

    private char value = '.';

    public DiscCell() {
        setPreferredSize(new Dimension(60, 60));
        setOpaque(false);
    }

    public void setValue(char value) {
        this.value = value;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // blue board background
        g2.setColor(new Color(25, 45, 160));
        g2.fillRect(0, 0, width, height);

        // decide disc color
        if (value == 'R') {
            g2.setColor(Color.RED);
        } else if (value == 'B') {
            g2.setColor(Color.YELLOW); // classic Connect 4 look
            // if you want true blue pieces instead, use Color.BLUE
        } else {
            g2.setColor(Color.WHITE);
        }

        int margin = 12;
        int size = Math.min(width, height) - 2 * margin;
        g2.fillOval((width - size) / 2, (height - size) / 2, size, size);

        // subtle outline
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(margin, margin, width - 2 * margin, height - 2 * margin);

        g2.dispose();
    }
}