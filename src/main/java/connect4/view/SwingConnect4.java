package connect4.view;

import connect4.board.Board;
import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.controller.MoveResult;
import connect4.factory.PieceFactory;
import connect4.factory.PlayerFactory;
import connect4.player.Player;
import connect4.strategy.HumanStrategy;
import connect4.strategy.RandomStrategy;
import connect4.strategy.StandardWinStrategy;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SwingConnect4 implements BoardView {

    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color EMPTY_CELL_COLOR = Color.WHITE;
    private static final Color BOARD_BORDER_COLOR = new Color(200, 200, 200);
    private static final Color STATUS_COLOR = Color.BLACK;
    private static final int CELL_SIZE = 70;

    private final Connect4Game game;

    private final JFrame frame = new JFrame("Connect 4");
    private final JPanel boardPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JLabel message = new JLabel("Welcome to Connect 4.");

    private final JLabel[][] cells;
    private final JButton[] columnButtons;

    public SwingConnect4(Connect4Game game) {
        this.game = game;

        int rows = game.getBoard().getRows();
        int cols = game.getBoard().getCols();

        this.cells = new JLabel[rows][cols];
        this.columnButtons = new JButton[cols];

        buildUi(rows, cols);
        display(game.getBoard());
        updateMessage();
    }

    private void buildUi(int rows, int cols) {
        buttonPanel.setLayout(new GridLayout(1, cols, 6, 6));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        for (int c = 0; c < cols; c++) {
            final int column = c;
            JButton button = new JButton("Drop " + c);
            button.addActionListener(e -> handleMove(column));
            columnButtons[c] = button;
            buttonPanel.add(button);
        }

        boardPanel.setLayout(new GridLayout(rows, cols, 6, 6));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
        boardPanel.setBackground(BACKGROUND_COLOR);

        Font font = new Font("SansSerif", Font.BOLD, 28);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setFont(font);
                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cell.setBorder(new LineBorder(BOARD_BORDER_COLOR, 2));
                cell.setBackground(EMPTY_CELL_COLOR);
                cells[r][c] = cell;
                boardPanel.add(cell);
            }
        }

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        bottom.add(message, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void handleMove(int column) {
        if (game.isGameOver()) {
            return;
        }

        MoveResult result = game.makeMove(column);

        if (result == MoveResult.INVALID) {
            message.setText("Invalid move. Try another column.");
            return;
        }

        display(game.getBoard());

        if (game.isGameOver()) {
            if (game.getWinner() != null) {
                message.setText(game.getWinner().getName() + " wins!");
            } else {
                message.setText("The game is a draw.");
            }
            disableColumnButtons();
            return;
        }

        updateMessage();

        maybePlayAiTurn();
    }

    private void maybePlayAiTurn() {
        if (game.isGameOver()) {
            return;
        }

        Player current = game.getCurrentPlayer();

        if (!(current.getStrategy() instanceof HumanStrategy)) {
            int column = current.getStrategy().selectColumn(game.getBoard(), current);
            MoveResult result = game.makeMove(column);

            if (result != MoveResult.INVALID) {
                display(game.getBoard());

                if (game.isGameOver()) {
                    if (game.getWinner() != null) {
                        message.setText(game.getWinner().getName() + " wins!");
                    } else {
                        message.setText("The game is a draw.");
                    }
                    disableColumnButtons();
                } else {
                    updateMessage();
                }
            }
        }
    }

    @Override
    public void display(Board board) {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                JLabel cell = cells[r][c];
                char value = board.getCell(r, c);

                cell.setText("");
                cell.setForeground(Color.BLACK);

                if (value == 'R') {
                    cell.setBackground(Color.RED);
                } else if (value == 'B') {
                    cell.setBackground(Color.BLUE);
                } else {
                    cell.setBackground(EMPTY_CELL_COLOR);
                }
            }
        }
        frame.repaint();
    }

    private void updateMessage() {
        message.setForeground(STATUS_COLOR);
        message.setText("It is " + game.getCurrentPlayer().getName() + "'s turn.");
    }

    private void disableColumnButtons() {
        for (JButton button : columnButtons) {
            button.setEnabled(false);
        }
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public static void main(String[] args) {
        GridBoard board = new GridBoard(6, 7);

        PieceFactory pieceFactory = new PieceFactory();
        PlayerFactory playerFactory = new PlayerFactory();

        Player player1 = playerFactory.createPlayer(
                "Player 1",
                pieceFactory.createPiece("red"),
                new HumanStrategy()
        );

        Player player2 = playerFactory.createPlayer(
                "Player 2",
                pieceFactory.createPiece("blue"),
                new HumanStrategy()
                // change to new RandomStrategy() for human vs AI
        );

        Connect4Game game = new Connect4Game(
                board,
                new Player[]{player1, player2},
                new StandardWinStrategy()
        );

        new SwingConnect4(game).show();
    }
}