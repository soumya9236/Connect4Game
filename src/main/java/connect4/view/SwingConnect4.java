//package connect4.view;
//
//import connect4.board.Board;
//import connect4.board.GridBoard;
//import connect4.controller.Connect4Game;
//import connect4.controller.MoveResult;
//import connect4.factory.PieceFactory;
//import connect4.factory.PlayerFactory;
//import connect4.player.Player;
//import connect4.strategy.HumanStrategy;
//import connect4.strategy.RandomStrategy;
//import connect4.strategy.StandardWinStrategy;
//import connect4.strategy.CleverStrategy;
//import connect4.strategy.MasterStrategy;
//import connect4.strategy.MoveStrategy;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//
//public class SwingConnect4 implements BoardView {
//
//    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
//    private static final Color EMPTY_CELL_COLOR = Color.WHITE;
//    private static final Color BOARD_BORDER_COLOR = new Color(200, 200, 200);
//    private static final Color STATUS_COLOR = Color.BLACK;
//    private static final int CELL_SIZE = 70;
//
//    private final Connect4Game game;
//
//    private final JFrame frame = new JFrame("Connect 4");
//    private final JPanel boardPanel = new JPanel();
//    private final JPanel buttonPanel = new JPanel();
//    private final JLabel message = new JLabel("Welcome to Connect 4.");
//
//    //private final JLabel[][] cells;
//    private final DiscCell[][] cells;
//    private final JButton[] columnButtons;
//
//    public SwingConnect4(Connect4Game game) {
//        this.game = game;
//
//        int rows = game.getBoard().getRows();
//        int cols = game.getBoard().getCols();
//
//        //this.cells = new JLabel[rows][cols];
//        this.cells = new DiscCell[rows][cols];
//        this.columnButtons = new JButton[cols];
//
//        buildUi(rows, cols);
//        display(game.getBoard());
//        updateMessage();
//    }
//
//    private void buildUi(int rows, int cols) {
//        buttonPanel.setLayout(new GridLayout(1, cols, 10, 10));
//        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        buttonPanel.setBackground(BACKGROUND_COLOR);
//
//        for (int c = 0; c < cols; c++) {
//            final int column = c;
//            JButton button = new JButton("Drop " + c);
//            button.addActionListener(e -> handleMove(column));
//            columnButtons[c] = button;
//            buttonPanel.add(button);
//        }
//
//        boardPanel.setLayout(new GridLayout(rows, cols, 6, 6));
//        boardPanel.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
//        boardPanel.setBackground(new Color(160, 160, 160));
//
//        Font font = new Font("SansSerif", Font.BOLD, 28);
//
//        for (int r = 0; r < rows; r++) {
//            for (int c = 0; c < cols; c++) {
////                JLabel cell = new JLabel("", SwingConstants.CENTER);
////                cell.setOpaque(true);
////                cell.setFont(font);
////                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
////                cell.setBorder(new LineBorder(BOARD_BORDER_COLOR, 2));
////                cell.setBackground(EMPTY_CELL_COLOR);
////                cells[r][c] = cell;
////                boardPanel.add(cell);
//                DiscCell cell = new DiscCell();
//                cells[r][c] = cell;
//                boardPanel.add(cell);
//            }
//        }
//
//        JPanel bottom = new JPanel(new BorderLayout());
//        bottom.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
//        bottom.add(message, BorderLayout.CENTER);
//
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//        frame.add(buttonPanel, BorderLayout.NORTH);
//        frame.add(boardPanel, BorderLayout.CENTER);
//        frame.add(bottom, BorderLayout.SOUTH);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//    }
//
//    private void handleMove(int column) {
//        if (game.isGameOver()) {
//            return;
//        }
//
//        MoveResult result = game.makeMove(column);
//
//        if (result == MoveResult.INVALID) {
//            message.setText("Invalid move. Try another column.");
//            return;
//        }
//
//        display(game.getBoard());
//
//        if (game.isGameOver()) {
//            if (game.getWinner() != null) {
//                message.setText(game.getWinner().getName() + " wins!");
//            } else {
//                message.setText("The game is a draw.");
//            }
//            disableColumnButtons();
//            return;
//        }
//
//        updateMessage();
//
//        maybePlayAiTurn();
//    }
//
//    private void maybePlayAiTurn() {
//        if (game.isGameOver()) {
//            return;
//        }
//
//        Player current = game.getCurrentPlayer();
//
//        if (!(current.getStrategy() instanceof HumanStrategy)) {
//            int column = current.getStrategy().selectColumn(game.getBoard(), current);
//            MoveResult result = game.makeMove(column);
//
//            if (result != MoveResult.INVALID) {
//                display(game.getBoard());
//
//                if (game.isGameOver()) {
//                    if (game.getWinner() != null) {
//                        message.setText(game.getWinner().getName() + " wins!");
//                    } else {
//                        message.setText("The game is a draw.");
//                    }
//                    disableColumnButtons();
//                } else {
//                    updateMessage();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void display(Board board) {
//        for (int r = 0; r < board.getRows(); r++) {
//            for (int c = 0; c < board.getCols(); c++) {
//                cells[r][c].setValue(board.getCell(r, c));
//            }
//        }
//
//        boardPanel.repaint();
//    }
//
//    private void updateMessage() {
//        message.setForeground(STATUS_COLOR);
//        message.setText("It is " + game.getCurrentPlayer().getName() + "'s turn.");
//    }
//
//    private void disableColumnButtons() {
//        for (JButton button : columnButtons) {
//            button.setEnabled(false);
//        }
//    }
//
//    public void show() {
//        SwingUtilities.invokeLater(() -> frame.setVisible(true));
//    }
//
//    public static void main(String[] args) {
//        GridBoard board = new GridBoard(6, 7);
//
//        PieceFactory pieceFactory = new PieceFactory();
//        PlayerFactory playerFactory = new PlayerFactory();
//
//        Player player1 = playerFactory.createPlayer(
//                "Player 1",
//                pieceFactory.createPiece("red"),
//                new HumanStrategy()
//        );
//
//        Player player2 = playerFactory.createPlayer(
//                "Player 2",
//                pieceFactory.createPiece("blue"),
//                new HumanStrategy()
//                // change to new RandomStrategy() for human vs AI
//        );
//
//        Connect4Game game = new Connect4Game(
//                board,
//                new Player[]{player1, player2},
//                new StandardWinStrategy()
//        );
//
//        new SwingConnect4(game).show();
//    }
//}

package connect4.view;

import connect4.board.Board;
import connect4.command.DropPieceCommand;
import connect4.command.GameCommand;
import connect4.controller.Connect4Game;
import connect4.controller.MoveResult;
import connect4.player.Player;
import connect4.strategy.HumanStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

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

    private final DiscCell[][] cells;
    private final JButton[] columnButtons;

    private final Deque<GameCommand> commandHistory = new ArrayDeque<>();
    private JButton undoButton;
    private int redUndosLeft = 3;
    private int blueUndosLeft = 3;

    public SwingConnect4(Connect4Game game) {
        this.game = game;

        int rows = game.getBoard().getRows();
        int cols = game.getBoard().getCols();

        this.cells = new DiscCell[rows][cols];
        this.columnButtons = new JButton[cols];

        buildUi(rows, cols);
        display(game.getBoard());
        updateMessage();

        // If player 1 is AI (unlikely but supported), auto-play immediately
        maybePlayAiTurn();
    }
    private void buildUi(int rows, int cols) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);

        buttonPanel.setLayout(new GridLayout(1, cols, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        for (int c = 0; c < cols; c++) {
            final int column = c;
            JButton button = new JButton("Drop " + c);
            button.addActionListener(e -> handleMove(column));
            columnButtons[c] = button;
            buttonPanel.add(button);
        }

        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> handleUndo());

        JPanel undoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        undoPanel.setBackground(BACKGROUND_COLOR);
        undoPanel.add(undoButton);

        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(undoPanel, BorderLayout.EAST);

        boardPanel.setLayout(new GridLayout(rows, cols, 6, 6));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));
        boardPanel.setBackground(new Color(160, 160, 160));

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                DiscCell cell = new DiscCell();
                cells[r][c] = cell;
                boardPanel.add(cell);
            }
        }

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        bottom.add(message, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }


    private void handleMove(int column) {
        if (game.isGameOver()) return;

        if (!(game.getCurrentPlayer().getStrategy() instanceof HumanStrategy)) return;

        GameCommand command = new DropPieceCommand(game, column);
        MoveResult result = command.execute();

        if (result == MoveResult.INVALID) {
            message.setText("Invalid move. Try another column.");
            return;
        }

        if (result == MoveResult.SUCCESS || result == MoveResult.WIN || result == MoveResult.DRAW) {
            commandHistory.push(command);
        }

        display(game.getBoard());

        if (game.isGameOver()) {
            showGameOver();
            return;
        }

        updateMessage();
        maybePlayAiTurn();
    }



    private void maybePlayAiTurn() {
        if (game.isGameOver()) return;

        Player current = game.getCurrentPlayer();
        if (current.getStrategy() instanceof HumanStrategy) return;

        // Small delay so the human can see the board update before AI moves
        Timer timer = new Timer(400, e -> {
            if (game.isGameOver()) return;

            int column = current.getStrategy().selectColumn(game.getBoard(), current);
            GameCommand command = new DropPieceCommand(game, column);
            MoveResult result = command.execute();

            if (result == MoveResult.SUCCESS || result == MoveResult.WIN || result == MoveResult.DRAW) {
                commandHistory.push(command);
            }

            if (result != MoveResult.INVALID) {
                display(game.getBoard());

                if (game.isGameOver()) {
                    showGameOver();
                } else {
                    updateMessage();
                    maybePlayAiTurn();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void display(Board board) {
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                cells[r][c].setValue(board.getCell(r, c));
            }
        }
        boardPanel.repaint();
    }

    private void updateMessage() {
        message.setForeground(STATUS_COLOR);
        Player current = game.getCurrentPlayer();
        boolean isAi = !(current.getStrategy() instanceof HumanStrategy);
        if (isAi) {
            message.setText(current.getName() + " is thinking...");
        } else {
            message.setText("It is " + current.getName() + "'s turn.");
        }
    }

    private void showGameOver() {
        disableColumnButtons();

        String messageText;
        String title;

        if (game.getWinner() != null) {
            messageText = game.getWinner().getName() + " wins! 🎉";
            title = "Winner!";
            message.setText(messageText);
        } else {
            messageText = "It's a draw!";
            title = "Draw";
            message.setText(messageText);
        }

        String[] options = {"Play Again", "Exit"};

        int choice = showEndGameDialog(messageText, title);

        if (choice == 0) {
            frame.dispose();

            SwingUtilities.invokeLater(() -> {
                GameSetupDialog setup = new GameSetupDialog(null);
                Connect4Game newGame = setup.getConfiguredGame();

                if (newGame == null) {
                    exitApplication();
                }

                new SwingConnect4(newGame).show();
            });
        } else {
            frame.dispose();
            exitApplication();
        }
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
        SwingUtilities.invokeLater(() -> {
            GameSetupDialog setup = new GameSetupDialog(null);
            Connect4Game game = setup.getConfiguredGame();

            exitIfNoGame(game);

            new SwingConnect4(game).show();
        });
    }
    protected int showEndGameDialog(String messageText, String title) {
        String[] options = {"Play Again", "Exit"};

        return JOptionPane.showOptionDialog(
                frame,
                messageText,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    protected void exitApplication() {
        System.exit(0);
    }
    static void exitIfNoGame(Connect4Game game) {
        if (game == null) {
            System.exit(0);
        }
    }
    private void handleUndo() {
        if (commandHistory.isEmpty()) {
            message.setText("Nothing to undo.");
            return;
        }

        if (getUndosLeftForCurrentPlayer() <= 0) {
            message.setText(game.getCurrentPlayer().getName() + " has no undos left.");
            return;
        }

        useUndoForCurrentPlayer();


        for (JButton button : columnButtons) {
            button.setEnabled(true);
        }

        GameCommand lastCommand = commandHistory.pop();
        lastCommand.undo();

        if (!commandHistory.isEmpty()
                && !(game.getCurrentPlayer().getStrategy() instanceof HumanStrategy)) {
            GameCommand previousCommand = commandHistory.pop();
            previousCommand.undo();
        }

        display(game.getBoard());
        updateMessage();
    }
    private int getUndosLeftForCurrentPlayer() {
        char symbol = game.getCurrentPlayer().getPiece().getSymbol();
        return (symbol == 'R') ? redUndosLeft : blueUndosLeft;
    }

    private void useUndoForCurrentPlayer() {
        char symbol = game.getCurrentPlayer().getPiece().getSymbol();
        if (symbol == 'R') {
            redUndosLeft--;
        } else {
            blueUndosLeft--;
        }
    }
}