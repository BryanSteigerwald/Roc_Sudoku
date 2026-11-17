package solver;

import model.Board;
import model.CellPosition;
import rules.SudokuValidator;

import java.util.Optional;

public class SolverHintProvider implements HintProvider {

    private final SolverStrategy solver;
    private final SudokuValidator validator;

    public SolverHintProvider(
            SolverStrategy solver,
            SudokuValidator validator
    ) {
        if (solver == null || validator == null) {
            throw new IllegalArgumentException(
                    "Solver and validator cannot be null."
            );
        }

        this.solver = solver;
        this.validator = validator;
    }

    @Override
    public Optional<Hint> getHint(Board board) {
        if (board == null || !validator.isValidBoard(board)) {
            return Optional.empty();
        }

        Board solvedBoard = board.copy();

        if (!solver.solve(solvedBoard)) {
            return Optional.empty();
        }

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.isEmpty(row, col)) {
                    return Optional.of(new Hint(
                            new CellPosition(row, col),
                            solvedBoard.getValue(row, col)
                    ));
                }
            }
        }

        return Optional.empty();
    }
}