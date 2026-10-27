package rules;

import model.Board;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuValidatorTest {

    @Test
    void testConstructorRejectsNullRules() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SudokuValidator(null)
        );
    }

    @Test
    void testConstructorRejectsEmptyRules() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SudokuValidator(List.of())
        );
    }

    @Test
    void testNullBoardReturnsFalse() {
        SudokuValidator validator =
                new SudokuValidator(List.of(new RowRule()));

        assertFalse(validator.isValidBoard(null));
    }

    @Test
    void testValidBoardReturnsTrue() {
        Board board = new Board();

        SudokuValidator validator =
                new SudokuValidator(
                        List.of(
                                new RowRule(),
                                new ColumnRule(),
                                new BoxRule()
                        )
                );

        assertTrue(validator.isValidBoard(board));
    }

    @Test
    void testInvalidBoardReturnsFalse() {
        int[][] values = new int[9][9];
        values[0][0] = 5;
        values[0][1] = 5;

        Board board = new Board(values);

        SudokuValidator validator =
                new SudokuValidator(
                        List.of(new RowRule())
                );

        assertFalse(validator.isValidBoard(board));
    }
}