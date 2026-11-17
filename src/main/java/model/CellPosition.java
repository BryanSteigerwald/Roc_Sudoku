package model;

public record CellPosition(int row, int col) {

    public CellPosition {
        if (row < 0 || row >= Board.SIZE
                || col < 0 || col >= Board.SIZE) {
            throw new IllegalArgumentException(
                    "Cell position must be within the 9x9 board."
            );
        }
    }
}