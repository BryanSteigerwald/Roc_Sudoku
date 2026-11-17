package model;

public record Conflict(
        String ruleName,
        CellPosition firstCell,
        CellPosition secondCell,
        int value
) {
}