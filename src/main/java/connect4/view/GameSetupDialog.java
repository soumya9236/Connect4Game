package connect4.view;

import connect4.board.GridBoard;
import connect4.controller.Connect4Game;
import connect4.factory.PieceFactory;
import connect4.factory.PlayerFactory;
import connect4.player.Player;
import connect4.strategy.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Setup dialog shown before the game starts.
 * Lets the user choose between Human vs Human or Human vs Computer.
 * If Human vs Computer is selected, the user can also pick a difficulty level.
 */
public class GameSetupDialog extends JDialog {

    // ── palette ──────────────────────────────────────────────────────────────
    private static final Color BG          = new Color(28, 28, 36);
    private static final Color PANEL_BG    = new Color(38, 38, 50);
    private static final Color ACCENT_RED  = new Color(220, 50,  50);
    private static final Color ACCENT_BLUE = new Color(50,  120, 220);
    private static final Color TEXT_LIGHT  = new Color(230, 230, 240);
    private static final Color TEXT_DIM    = new Color(140, 140, 160);
    private static final Color BTN_BG      = new Color(55,  55,  75);
    private static final Color BTN_HOVER   = new Color(75,  75,  100);

    private static final Font TITLE_FONT  = new Font("SansSerif", Font.BOLD,  22);
    private static final Font LABEL_FONT  = new Font("SansSerif", Font.BOLD,  13);
    private static final Font OPTION_FONT = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font BTN_FONT    = new Font("SansSerif", Font.BOLD,  14);

    // ── state ─────────────────────────────────────────────────────────────────
    private Connect4Game result = null;

    private final ButtonGroup modeGroup  = new ButtonGroup();
    private final ButtonGroup diffGroup  = new ButtonGroup();
    private       JPanel      diffPanel;

    public GameSetupDialog(Frame owner) {
        super(owner, "Connect 4 – Game Setup", true);
        buildUi();
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    // ── UI construction ───────────────────────────────────────────────────────

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(28, 32, 24, 32));

        // Title
        JLabel title = new JLabel("Connect 4", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_LIGHT);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        root.add(title, BorderLayout.NORTH);

        // Centre — mode + difficulty
        JPanel centre = new JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
        centre.setBackground(BG);

        centre.add(sectionLabel("Game Mode"));
        centre.add(Box.createVerticalStrut(8));
        centre.add(buildModePanel());
        centre.add(Box.createVerticalStrut(20));

        diffPanel = new JPanel();
        diffPanel.setLayout(new BoxLayout(diffPanel, BoxLayout.Y_AXIS));
        diffPanel.setBackground(BG);
        diffPanel.add(sectionLabel("Difficulty"));
        diffPanel.add(Box.createVerticalStrut(8));
        diffPanel.add(buildDiffPanel());
        diffPanel.setVisible(false);          // hidden until HvC selected
        centre.add(diffPanel);

        root.add(centre, BorderLayout.CENTER);

        // Start button
        JButton startBtn = styledButton("▶  Start Game");
        startBtn.addActionListener(e -> onStart());
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnRow.setBackground(BG);
        btnRow.setBorder(new EmptyBorder(24, 0, 0, 0));
        btnRow.add(startBtn);
        root.add(btnRow, BorderLayout.SOUTH);

        setContentPane(root);
        getContentPane().setBackground(BG);
    }

    private JPanel buildModePanel() {
        JPanel p = new JPanel(new GridLayout(1, 2, 12, 0));
        p.setBackground(BG);

        JRadioButton hvh = radioButton("👥  Human vs Human",  "hvh");
        JRadioButton hvc = radioButton("🤖  Human vs Computer", "hvc");

        hvh.setSelected(true);
        hvc.addActionListener(e -> { diffPanel.setVisible(true);  pack(); });
        hvh.addActionListener(e -> { diffPanel.setVisible(false); pack(); });

        modeGroup.add(hvh);
        modeGroup.add(hvc);
        p.add(hvh);
        p.add(hvc);
        return p;
    }

    private JPanel buildDiffPanel() {
        JPanel p = new JPanel(new GridLayout(1, 3, 12, 0));
        p.setBackground(BG);

        JRadioButton easy   = radioButton("Easy",   "easy");
        JRadioButton medium = radioButton("Medium", "medium");
        JRadioButton hard   = radioButton("Hard",   "hard");

        easy.setSelected(true);
        diffGroup.add(easy);
        diffGroup.add(medium);
        diffGroup.add(hard);
        p.add(easy);
        p.add(medium);
        p.add(hard);
        return p;
    }

    // ── event handling ────────────────────────────────────────────────────────

    private void onStart() {
        String mode = getSelected(modeGroup);
        String diff = getSelected(diffGroup);

        PieceFactory   pieceFactory  = new PieceFactory();
        PlayerFactory  playerFactory = new PlayerFactory();
        GridBoard      board         = new GridBoard(6, 7);

        Player player1 = playerFactory.createPlayer(
                "Player 1",
                pieceFactory.createPiece("red"),
                new HumanStrategy()
        );

        Player player2;
        if ("hvc".equals(mode)) {
            MoveStrategy aiStrategy = switch (diff) {
                case "medium" -> new CleverStrategy();
                case "hard"   -> new MasterStrategy();
                default       -> new RandomStrategy();   // easy
            };
            player2 = playerFactory.createPlayer(
                    "Computer (" + capitalize(diff) + ")",
                    pieceFactory.createPiece("blue"),
                    aiStrategy
            );
        } else {
            player2 = playerFactory.createPlayer(
                    "Player 2",
                    pieceFactory.createPiece("blue"),
                    new HumanStrategy()
            );
        }

        result = new Connect4Game(
                board,
                new Player[]{player1, player2},
                new StandardWinStrategy()
        );

        dispose();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(TEXT_DIM);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, lbl.getPreferredSize().height));
        return lbl;
    }

    private JRadioButton radioButton(String label, String command) {
        JRadioButton btn = new JRadioButton(label);
        btn.setActionCommand(command);
        btn.setFont(OPTION_FONT);
        btn.setForeground(TEXT_LIGHT);
        btn.setBackground(PANEL_BG);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 80), 1),
                new EmptyBorder(10, 14, 10, 14)
        ));
        btn.setFocusPainted(false);
        return btn;
    }

    private JButton styledButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(BTN_FONT);
        btn.setForeground(Color.WHITE);
        btn.setBackground(ACCENT_RED);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(12, 36, 12, 36));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT_RED.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ACCENT_RED);
            }
        });
        return btn;
    }

    private String getSelected(ButtonGroup group) {
        var e = group.getElements();
        while (e.hasMoreElements()) {
            AbstractButton btn = e.nextElement();
            if (btn.isSelected()) return btn.getActionCommand();
        }
        return null;
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    // ── public API ────────────────────────────────────────────────────────────

    /**
     * Shows the dialog and blocks until the user clicks Start.
     *
     * @return the configured {@link Connect4Game}, or {@code null} if the
     *         dialog was closed without starting.
     */
    public Connect4Game getConfiguredGame() {
        setVisible(true);   // blocks (modal)
        return result;
    }
}