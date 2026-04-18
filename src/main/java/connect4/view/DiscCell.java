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


        g2.setColor(new Color(180, 180, 180));
        g2.fillRect(0, 0, width, height);

        // decide disc color
        if (value == 'R') {
            g2.setColor(Color.RED);
        } else if (value == 'B') {
            g2.setColor(Color.BLUE);
        } else {
            g2.setColor(Color.WHITE);
        }

        int margin = 12;
        int size = Math.min(width, height) - 2 * margin;

        int x = (width - size) / 2;
        int y = (height - size) / 2;


        g2.fillOval(x, y, size, size);


        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(x, y, size, size);

        g2.dispose();
    }
}