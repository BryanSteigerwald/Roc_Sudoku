package solver;

import model.Board;
import org.junit.jupiter.api.Test;
import rules.SudokuValidator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SolverHintProviderTest {

    @Test
    void hintReturnsValueForEmptyCell() {
        SudokuValidator validator = new SudokuValidator();
        BacktrackingSolver solver = new BacktrackingSolver(validator);

        HintProvider provider =
                new SolverHintProvider(solver, validator);

        Board board = new Board();

        board.setValue(0, 0, 1);
        board.setValue(0, 1, 2);
        board.setValue(0, 2, 3);
        board.setValue(0, 3, 4);
        board.setValue(0, 4, 5);
        board.setValue(0, 5, 6);
        board.setValue(0, 6, 7);
        board.setValue(0, 7, 8);

        Optional<Hint> hint = provider.getHint(board);

        assertTrue(hint.isPresent());
        assertEquals(0, hint.get().position().row());
        assertEquals(8, hint.get().position().col());
        assertEquals(9, hint.get().value());
    }

    @Test
    void hintDoesNotModifyOriginalBoard() {
        SudokuValidator validator = new SudokuValidator();
        BacktrackingSolver solver = new BacktrackingSolver(validator);

        HintProvider provider =
                new SolverHintProvider(solver, validator);

        Board board = new Board();

        board.setValue(0, 0, 1);
        board.setValue(0, 1, 2);

        int[][] before = board.getValues();

        provider.getHint(board);

        assertArrayEquals(before, board.getValues());
    }

    @Test
    void solvedBoardHasNoHint() {
        SudokuValidator validator = new SudokuValidator();
        BacktrackingSolver solver = new BacktrackingSolver(validator);

        HintProvider provider =
                new SolverHintProvider(solver, validator);

        Board board = new Board(new int[][]{
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        });

        assertTrue(provider.getHint(board).isEmpty());
    }
}