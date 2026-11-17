package rules;

import model.Board;
import model.CellPosition;
import model.Conflict;

import java.util.ArrayList;
import java.util.List;

public class ColumnRule implements Rule {

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

        for (int col = 0; col < Board.SIZE; col++) {
            for (int firstRow = 0; firstRow < Board.SIZE; firstRow++) {
                int value = board.getValue(firstRow, col);

                if (value == Board.EMPTY) {
                    continue;
                }

                for (int secondRow = firstRow + 1;
                     secondRow < Board.SIZE;
                     secondRow++) {

                    if (board.getValue(secondRow, col) == value) {
                        conflicts.add(new Conflict(
                                "Column",
                                new CellPosition(firstRow, col),
                                new CellPosition(secondRow, col),
                                value
                        ));
                    }
                }
            }
        }

        return conflicts;
    }
}