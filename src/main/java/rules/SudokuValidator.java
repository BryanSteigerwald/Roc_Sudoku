package rules;

import model.Board;
import model.Conflict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuValidator {

    private final List<Rule> rules;

    public SudokuValidator() {
        this(List.of(
                new RowRule(),
                new ColumnRule(),
                new BoxRule()
        ));
    }

    public SudokuValidator(List<Rule> rules) {
        if (rules == null) {
            throw new IllegalArgumentException("Rules cannot be null.");
        }

        if (rules.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one validation rule is required."
            );
        }

        this.rules = new ArrayList<>(rules);
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

    public List<Conflict> getConflicts(Board board) {
        if (board == null) {
            return Collections.emptyList();
        }

        List<Conflict> conflicts = new ArrayList<>();

        for (Rule rule : rules) {
            conflicts.addAll(rule.findConflicts(board));
        }

        return Collections.unmodifiableList(conflicts);
    }

    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }
}