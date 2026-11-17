package rules;

import model.Board;
import model.CellPosition;
import model.Conflict;

import java.util.ArrayList;
import java.util.List;

public class BoxRule implements Rule {

    private static final int BOX_SIZE = 3;

    @Override
    public boolean isValid(Board board) {
        return board != null && findConflicts(board).isEmpty();
    }

    @Override
    public List<Conflict> findConflicts(Board board) {
        List<Conflict> conflicts = new ArrayList<>();

        if (board == null) {
            return conflicts;
        }

        for (int boxRow = 0; boxRow < Board.SIZE; boxRow += BOX_SIZE) {
            for (int boxCol = 0; boxCol < Board.SIZE; boxCol += BOX_SIZE) {

                for (int row = boxRow; row < boxRow + BOX_SIZE; row++) {
                    for (int col = boxCol; col < boxCol + BOX_SIZE; col++) {

                        int value = board.getValue(row, col);

                        if (value == Board.EMPTY) {
                            continue;
                        }

                        for (int otherRow = row;
                             otherRow < boxRow + BOX_SIZE;
                             otherRow++) {

                            for (int otherCol = boxCol;
                                 otherCol < boxCol + BOX_SIZE;
                                 otherCol++) {

                                if (otherRow < row
                                        || (otherRow == row && otherCol <= col)) {
                                    continue;
                                }

                                if (board.getValue(otherRow, otherCol) == value) {
                                    conflicts.add(new Conflict(
                                            "Box",
                                            new CellPosition(row, col),
                                            new CellPosition(otherRow, otherCol),
                                            value
                                    ));
                                }
                            }
                        }
                    }
                }
            }
        }

        return conflicts;
    }
}