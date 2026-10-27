package solver;

import factory.EasyPuzzleFactory;
import model.Board;
import org.junit.jupiter.api.Test;
import rules.BoxRule;
import rules.ColumnRule;
import rules.RowRule;
import rules.SudokuValidator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSolverTest {

    private SudokuValidator createValidator() {
        return new SudokuValidator(
                List.of(
                        new RowRule(),
                        new ColumnRule(),
                        new BoxRule()
                )
        );
    }

    @Test
    void testConstructorRejectsNullValidator() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new BacktrackingSolver(null)
        );
    }

    @Test
    void testSolveNullBoardReturnsFalse() {
        BacktrackingSolver solver =
                new BacktrackingSolver(createValidator());

        assertFalse(solver.solve(null));
    }

    @Test
    void testSolveInvalidBoardReturnsFalse() {
        int[][] values = new int[9][9];

        values[0][0] = 5;
        values[0][1] = 5;

        Board board = new Board(values);

        BacktrackingSolver solver =
                new BacktrackingSolver(createValidator());

        assertFalse(solver.solve(board));
    }

    @Test
    void testSolveEasyPuzzle() {
        EasyPuzzleFactory factory = new EasyPuzzleFactory();

        Board board = factory.createPuzzle();

        BacktrackingSolver solver =
                new BacktrackingSolver(createValidator());

        assertTrue(solver.solve(board));
        assertTrue(board.isFull());
    }

    @Test
    void testAlreadySolvedBoardReturnsTrue() {
        int[][] solvedBoard = {
                {5,3,4,6,7,8,9,1,2},
                {6,7,2,1,9,5,3,4,8},
                {1,9,8,3,4,2,5,6,7},
                {8,5,9,7,6,1,4,2,3},
                {4,2,6,8,5,3,7,9,1},
                {7,1,3,9,2,4,8,5,6},
                {9,6,1,5,3,7,2,8,4},
                {2,8,7,4,1,9,6,3,5},
                {3,4,5,2,8,6,1,7,9}
        };

        Board board = new Board(solvedBoard);

        BacktrackingSolver solver =
                new BacktrackingSolver(createValidator());

        assertTrue(solver.solve(board));
    }
}