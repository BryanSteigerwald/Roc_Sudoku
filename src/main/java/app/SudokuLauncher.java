
package app;

import factory.EasyPuzzleFactory;
import factory.PuzzleFactory;
import rules.BoxRule;
import rules.ColumnRule;
import rules.RowRule;
import rules.Rule;
import rules.SudokuValidator;
import solver.BacktrackingSolver;
import solver.SolverStrategy;
import view.SudokuSwingUI;


import javax.swing.SwingUtilities;
import java.util.List;

public class SudokuLauncher {
    public static void main(String[] args) {
        PuzzleFactory puzzleFactory = new EasyPuzzleFactory();

        List<Rule> rules = List.of(
                new RowRule(),
                new ColumnRule(),
                new BoxRule()
        );

        SudokuValidator validator = new SudokuValidator(rules);
        SolverStrategy solver = new BacktrackingSolver(validator);

        SwingUtilities.invokeLater(() -> {
            SudokuSwingUI ui = new SudokuSwingUI(
                    puzzleFactory,
                    validator,
                    solver
            );
            ui.setVisible(true);
        });
    }
}
