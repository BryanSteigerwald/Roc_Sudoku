
package rules;

import model.Board;

public interface Rule {
    boolean isValid(Board board);
}
