package rules;

import model.Board;
import model.Conflict;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConflictTest {

    @Test
    void rowRuleFindsDuplicate() {
        Board board = new Board();

        board.setValue(0, 0, 5);
        board.setValue(0, 4, 5);

        RowRule rule = new RowRule();

        List<Conflict> conflicts = rule.findConflicts(board);

        assertEquals(1, conflicts.size());
        assertEquals("Row", conflicts.get(0).ruleName());
        assertEquals(5, conflicts.get(0).value());
    }

    @Test
    void columnRuleFindsDuplicate() {
        Board board = new Board();

        board.setValue(0, 2, 7);
        board.setValue(5, 2, 7);

        ColumnRule rule = new ColumnRule();

        List<Conflict> conflicts = rule.findConflicts(board);

        assertEquals(1, conflicts.size());
        assertEquals("Column", conflicts.get(0).ruleName());
    }

    @Test
    void boxRuleFindsDuplicate() {
        Board board = new Board();

        board.setValue(0, 0, 3);
        board.setValue(2, 2, 3);

        BoxRule rule = new BoxRule();

        List<Conflict> conflicts = rule.findConflicts(board);

        assertEquals(1, conflicts.size());
        assertEquals("Box", conflicts.get(0).ruleName());
    }

    @Test
    void emptyCellsDoNotCauseConflicts() {
        Board board = new Board();

        SudokuValidator validator = new SudokuValidator();

        assertTrue(validator.isValidBoard(board));
        assertTrue(validator.getConflicts(board).isEmpty());
    }

    @Test
    void validatorCollectsConflicts() {
        Board board = new Board();

        board.setValue(0, 0, 4);
        board.setValue(0, 1, 4);
        board.setValue(1, 0, 4);

        SudokuValidator validator = new SudokuValidator();

        assertFalse(validator.isValidBoard(board));
        assertFalse(validator.getConflicts(board).isEmpty());
    }
}