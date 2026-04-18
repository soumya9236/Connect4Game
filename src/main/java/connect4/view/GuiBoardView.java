package connect4.view;

import connect4.board.Board;

import javax.swing.*;
import java.awt.*;

public class GuiBoardView extends JFrame implements BoardView {

    private final JPanel[][] cells;
    private final int rows;
    private final int cols;

    public GuiBoardView(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new JPanel[rows][cols];

        setTitle("Connect 4");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(rows, cols));

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JPanel cell = new JPanel();
                cell.setBackground(Color.WHITE);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[r][c] = cell;
                add(cell);
            }
        }

        setVisible(true);
    }

    @Override
    public void display(Board board) {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                char value = board.getCell(r, c);

                if (value == 'R') {
                    cells[r][c].setBackground(Color.RED);
                } else if (value == 'B') {
                    cells[r][c].setBackground(Color.BLUE);
                } else {
                    cells[r][c].setBackground(Color.WHITE);
                }
            }
        }

        repaint();
    }
}