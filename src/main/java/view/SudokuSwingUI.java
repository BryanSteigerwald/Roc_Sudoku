package view;

import factory.PuzzleFactory;
import model.Board;
import rules.SudokuValidator;
import solver.SolverStrategy;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SudokuSwingUI extends JFrame {
    private static final int SIZE = 9;

    private final PuzzleFactory puzzleFactory;
    private final SudokuValidator validator;
    private final SolverStrategy solver;

    private final JTextField[][] cells = new JTextField[SIZE][SIZE];
    private final JLabel statusLabel = new JLabel("Enter numbers to play.");

    private Board board;
    private boolean[][] givenCells;
    private boolean rendering;

    public SudokuSwingUI(
            PuzzleFactory puzzleFactory,
            SudokuValidator validator,
            SolverStrategy solver
    ) {
        this.puzzleFactory = puzzleFactory;
        this.validator = validator;
        this.solver = solver;

        setTitle("ROC Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createBoardPanel(), BorderLayout.CENTER);
        add(createControlsPanel(), BorderLayout.SOUTH);

        startNewGame();

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
                cell.setPreferredSize(new Dimension(55, 55));

                int top = row % 3 == 0 ? 3 : 1;
                int left = col % 3 == 0 ? 3 : 1;
                int bottom = row == SIZE - 1 ? 3 : 1;
                int right = col == SIZE - 1 ? 3 : 1;

                cell.setBorder(new MatteBorder(
                        top, left, bottom, right, Color.DARK_GRAY
                ));

                ((AbstractDocument) cell.getDocument())
                        .setDocumentFilter(new SudokuInputFilter());

                final int r = row;
                final int c = col;

                cell.getDocument().addDocumentListener(
                        new javax.swing.event.DocumentListener() {
                            private void updateValue() {
                                if (rendering || givenCells == null
                                        || givenCells[r][c]) {
                                    return;
                                }

                                String text = cell.getText();

                                int value = text.isEmpty()
                                        ? Board.EMPTY
                                        : Integer.parseInt(text);

                                board.setValue(r, c, value);
                                statusLabel.setText("Board updated.");
                            }

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent event) {
                                updateValue();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent event) {
                                updateValue();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent event) {
                                updateValue();
                            }
                        }
                );

                cells[row][col] = cell;
                panel.add(cell);
            }
        }

        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        return panel;
    }

    private JPanel createControlsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JPanel buttons = new JPanel(new FlowLayout());

        JButton newGameButton = new JButton("New Game");
        JButton checkButton = new JButton("Check Board");
        JButton solveButton = new JButton("Solve");

        newGameButton.addActionListener(this::handleNewGame);
        checkButton.addActionListener(this::handleCheck);
        solveButton.addActionListener(this::handleSolve);

        buttons.add(newGameButton);
        buttons.add(checkButton);
        buttons.add(solveButton);

        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(buttons, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));

        return panel;
    }

    private void startNewGame() {
        board = puzzleFactory.createPuzzle();
        int[][] initialValues = board.getValues();

        givenCells = new boolean[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                givenCells[row][col] = initialValues[row][col] != Board.EMPTY;
            }
        }

        renderBoard();
        statusLabel.setText("New puzzle ready.");
    }

    private void renderBoard() {
        rendering = true;

        try {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    JTextField cell = cells[row][col];
                    int value = board.getValue(row, col);

                    cell.setText(value == Board.EMPTY
                            ? ""
                            : Integer.toString(value));

                    cell.setEditable(!givenCells[row][col]);

                    if (givenCells[row][col]) {
                        cell.setBackground(new Color(225, 230, 238));
                        cell.setFont(new Font(
                                Font.SANS_SERIF, Font.BOLD, 24
                        ));
                    } else {
                        cell.setBackground(Color.WHITE);
                        cell.setFont(new Font(
                                Font.SANS_SERIF, Font.PLAIN, 24
                        ));
                    }
                }
            }
        } finally {
            rendering = false;
        }
    }

    private void handleNewGame(ActionEvent event) {
        startNewGame();
    }

    private void handleCheck(ActionEvent event) {
        if (!validator.isValidBoard(board)) {
            statusLabel.setText("Conflicts found. Check your numbers.");
        } else if (!board.isFull()) {
            statusLabel.setText("No conflicts so far. Keep going!");
        } else {
            statusLabel.setText("Congratulations! Sudoku solved!");
        }
    }

    private void handleSolve(ActionEvent event) {
        if (solver.solve(board)) {
            renderBoard();
            statusLabel.setText("Puzzle solved!");
        } else {
            statusLabel.setText("This puzzle could not be solved.");
        }
    }

    private static class SudokuInputFilter extends DocumentFilter {
        @Override
        public void insertString(
                FilterBypass bypass,
                int offset,
                String string,
                AttributeSet attributes
        ) throws BadLocationException {
            replace(bypass, offset, 0, string, attributes);
        }

        @Override
        public void replace(
                FilterBypass bypass,
                int offset,
                int length,
                String text,
                AttributeSet attributes
        ) throws BadLocationException {
            String current = bypass.getDocument().getText(
                    0, bypass.getDocument().getLength()
            );

            String replacement = text == null ? "" : text;
            String updated = current.substring(0, offset)
                    + replacement
                    + current.substring(offset + length);

            if (updated.isEmpty()
                    || updated.matches("[1-9]")) {
                bypass.replace(offset, length, replacement, attributes);
            }
        }
    }
}
