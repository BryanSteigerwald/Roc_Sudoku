
package model;

public class Board {
    public static final int SIZE = 9;
    public static final int EMPTY = 0;

    private final int[][] values;

    public Board() {
        values = new int[SIZE][SIZE];
    }

    public Board(int[][] initialValues) {
        if (initialValues == null || initialValues.length != SIZE) {
            throw new IllegalArgumentException("Board must have 9 rows.");
        }

        values = new int[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            if (initialValues[row] == null
                    || initialValues[row].length != SIZE) {
                throw new IllegalArgumentException(
                        "Each row must contain 9 values."
                );
            }

            for (int col = 0; col < SIZE; col++) {
                int value = initialValues[row][col];

                if (value < EMPTY || value > SIZE) {
                    throw new IllegalArgumentException(
                            "Cell values must be between 0 and 9."
                    );
                }

                values[row][col] = value;
            }
        }
    }

    public int getValue(int row, int col) {
        checkCoordinates(row, col);
        return values[row][col];
    }

    public void setValue(int row, int col, int value) {
        checkCoordinates(row, col);

        if (value < EMPTY || value > SIZE) {
            throw new IllegalArgumentException(
                    "Cell values must be between 0 and 9."
            );
        }

        values[row][col] = value;
    }

    public int[][] getValues() {
        int[][] copy = new int[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            System.arraycopy(values[row], 0, copy[row], 0, SIZE);
        }

        return copy;
    }

    public boolean isFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (values[row][col] == EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isEmpty(int row, int col) {
        return getValue(row, col) == EMPTY;
    }

    private void checkCoordinates(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IndexOutOfBoundsException(
                    "Row and column must be between 0 and 8."
            );
        }
    }
}
