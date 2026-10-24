
package rules;

import model.Board;

public class RowRule implements Rule {
    @Override
    public boolean isValid(Board board) {
        for (int row = 0; row < Board.SIZE; row++) {
            boolean[] seen = new boolean[Board.SIZE + 1];

            for (int col = 0; col < Board.SIZE; col++) {
                int value = board.getValue(row, col);

                if (value == Board.EMPTY) {
                    continue;
                }

                if (seen[value]) {
                    return false;
                }

                seen[value] = true;
            }
        }

        return true;
    }
}
