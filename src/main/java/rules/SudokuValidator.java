
package rules;

import java.util.List;
import model.Board;

public class SudokuValidator {
    private final List<Rule> rules;

    public SudokuValidator(List<Rule> rules) {
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one validation rule is required."
            );
        }

        this.rules = List.copyOf(rules);
    }

    public boolean isValidBoard(Board board) {
        if (board == null) {
            return false;
        }

        for (Rule rule : rules) {
            if (!rule.isValid(board)) {
                return false;
            }
        }

        return true;
    }
}
