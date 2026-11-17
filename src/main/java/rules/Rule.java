package rules;

import model.Board;
import model.Conflict;

import java.util.Collections;
import java.util.List;

public interface Rule {

    boolean isValid(Board board);

    default List<Conflict> findConflicts(Board board) {
        return Collections.emptyList();
    }
}