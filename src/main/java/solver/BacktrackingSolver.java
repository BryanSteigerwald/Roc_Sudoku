
package solver;

import model.Board;
import rules.SudokuValidator;

public class BacktrackingSolver implements SolverStrategy {
    private final SudokuValidator validator;

    public BacktrackingSolver(SudokuValidator validator) {
        if (validator == null) {
            throw new IllegalArgumentException(
                    "Validator cannot be null."
            );
        }

        this.validator = validator;
    }

    @Override
    public boolean solve(Board board) {
        if (board == null || !validator.isValidBoard(board)) {
            return false;
        }

        return solveNext(board);
    }

    private boolean solveNext(Board board) {
        int emptyRow = -1;
        int emptyCol = -1;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.getValue(row, col) == Board.EMPTY) {
                    emptyRow = row;
                    emptyCol = col;
                    break;
                }
            }

            if (emptyRow != -1) {
                break;
            }
        }

        if (emptyRow == -1) {
            return true;
        }

        for (int value = 1; value <= Board.SIZE; value++) {
            board.setValue(emptyRow, emptyCol, value);

            if (validator.isValidBoard(board) && solveNext(board)) {
                return true;
            }

            board.setValue(emptyRow, emptyCol, Board.EMPTY);
        }

        return false;
    }
}
