package rules;

import model.Board;
import model.CellPosition;
import model.Conflict;

import java.util.ArrayList;
import java.util.List;

public class RowRule implements Rule {

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

        for (int row = 0; row < Board.SIZE; row++) {
            for (int firstCol = 0; firstCol < Board.SIZE; firstCol++) {
                int value = board.getValue(row, firstCol);

                if (value == Board.EMPTY) {
                    continue;
                }

                for (int secondCol = firstCol + 1;
                     secondCol < Board.SIZE;
                     secondCol++) {

                    if (board.getValue(row, secondCol) == value) {
                        conflicts.add(new Conflict(
                                "Row",
                                new CellPosition(row, firstCol),
                                new CellPosition(row, secondCol),
                                value
                        ));
                    }
                }
            }
        }

        return conflicts;
    }
}