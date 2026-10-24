
package rules;

import model.Board;

public class ColumnRule implements Rule {
    @Override
    public boolean isValid(Board board) {
        for (int col = 0; col < Board.SIZE; col++) {
            boolean[] seen = new boolean[Board.SIZE + 1];

            for (int row = 0; row < Board.SIZE; row++) {
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
