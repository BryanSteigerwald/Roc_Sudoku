package solver;

import model.CellPosition;

public record Hint(CellPosition position, int value) {

    public Hint {
        if (position == null) {
            throw new IllegalArgumentException(
                    "Hint position cannot be null."
            );
        }

        if (value < 1 || value > 9) {
            throw new IllegalArgumentException(
                    "Hint value must be between 1 and 9."
            );
        }
    }
}