
package rules;

import model.Board;

public class BoxRule implements Rule {
    @Override
    public boolean isValid(Board board) {
        for (int boxRow = 0; boxRow < Board.SIZE; boxRow += 3) {
            for (int boxCol = 0; boxCol < Board.SIZE; boxCol += 3) {
                boolean[] seen = new boolean[Board.SIZE + 1];

                for (int row = boxRow; row < boxRow + 3; row++) {
                    for (int col = boxCol; col < boxCol + 3; col++) {
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
            }
        }

        return true;
    }
}
