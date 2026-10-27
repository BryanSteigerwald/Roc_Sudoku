package view;

import factory.EasyPuzzleFactory;
import org.junit.jupiter.api.Test;
import rules.BoxRule;
import rules.ColumnRule;
import rules.RowRule;
import rules.SudokuValidator;
import solver.BacktrackingSolver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuSwingUITest {

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
    void testUiCreation() {
        SudokuValidator validator = createValidator();

        BacktrackingSolver solver =
                new BacktrackingSolver(validator);

        SudokuSwingUI ui =
                new SudokuSwingUI(
                        new EasyPuzzleFactory(),
                        validator,
                        solver
                );

        assertNotNull(ui);
        assertEquals("ROC Sudoku", ui.getTitle());

        ui.dispose();
    }

    @Test
    void testUiIsNotResizable() {
        SudokuValidator validator = createValidator();

        BacktrackingSolver solver =
                new BacktrackingSolver(validator);

        SudokuSwingUI ui =
                new SudokuSwingUI(
                        new EasyPuzzleFactory(),
                        validator,
                        solver
                );

        assertFalse(ui.isResizable());

        ui.dispose();
    }
}