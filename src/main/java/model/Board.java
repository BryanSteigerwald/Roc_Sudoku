package model;

/**
 * Represents a 9x9 Sudoku board.
 *
 * The integer-based API is retained for compatibility with the
 * existing Swing UI and tests.
 */
public class Board {
    public static final int SIZE = 9;
    public static final int EMPTY = 0;

    private final Cell[][] cells;

    public Board() {
        cells = new Cell[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new Cell();
            }
        }
    }

    public Board(int[][] values) {
        this(values, null);
    }

    public Board(int[][] values, boolean[][] givens) {
        validateDimensions(values);

        if (givens != null) {
            validateDimensions(givens);
        }

        cells = new Cell[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                boolean given = givens == null
                        ? values[row][col] != EMPTY
                        : givens[row][col];

                cells[row][col] = new Cell(values[row][col], given);
            }
        }
    }

    public int getValue(int row, int col) {
        checkPosition(row, col);
        return cells[row][col].getValue();
    }

    public void setValue(int row, int col, int value) {
        checkPosition(row, col);
        cells[row][col].setValue(value);
    }

    public Cell getCell(int row, int col) {
        checkPosition(row, col);
        return cells[row][col];
    }

    public boolean isGiven(int row, int col) {
        checkPosition(row, col);
        return cells[row][col].isGiven();
    }

    public boolean isEmpty(int row, int col) {
        return getValue(row, col) == EMPTY;
    }

    public boolean isFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isEmpty(row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    public int[][] getValues() {
        int[][] values = new int[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                values[row][col] = getValue(row, col);
            }
        }

        return values;
    }

    public void markAsGiven(int row, int col) {
        checkPosition(row, col);

        if (isEmpty(row, col)) {
            throw new IllegalStateException(
                    "An empty cell cannot be marked as a given."
            );
        }

        cells[row][col].setGiven(true);
    }

    public Board copy() {
        Board copy = new Board();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                copy.cells[row][col] = cells[row][col].copy();
            }
        }

        return copy;
    }

    private static void validateDimensions(int[][] values) {
        if (values == null || values.length != SIZE) {
            throw new IllegalArgumentException(
                    "Board must contain exactly 9 rows."
            );
        }

        for (int[] row : values) {
            if (row == null || row.length != SIZE) {
                throw new IllegalArgumentException(
                        "Every board row must contain exactly 9 values."
                );
            }

            for (int value : row) {
                if (value < 0 || value > 9) {
                    throw new IllegalArgumentException(
                            "Board values must be between 0 and 9."
                    );
                }
            }
        }
    }

    private static void validateDimensions(boolean[][] values) {
        if (values == null || values.length != SIZE) {
            throw new IllegalArgumentException(
                    "Given matrix must contain exactly 9 rows."
            );
        }

        for (boolean[] row : values) {
            if (row == null || row.length != SIZE) {
                throw new IllegalArgumentException(
                        "Every given-matrix row must contain exactly 9 values."
                );
            }
        }
    }

    private static void checkPosition(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IndexOutOfBoundsException(
                    "Invalid board position: (" + row + ", " + col + ")"
            );
        }
    }
}