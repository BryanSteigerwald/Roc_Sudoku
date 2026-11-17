package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Cell {
    private int value;
    private boolean given;
    private final Set<Integer> pencilMarks;

    public Cell() {
        this(0, false);
    }

    public Cell(int value, boolean given) {
        validateValue(value);
        this.value = value;
        this.given = given;
        this.pencilMarks = new HashSet<>();
    }

    public int getValue() {
        return value;
    }

    public boolean isGiven() {
        return given;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public Set<Integer> getPencilMarks() {
        return Collections.unmodifiableSet(pencilMarks);
    }

    public void addPencilMark(int value) {
        validateNumber(value);
        if (isEmpty()) {
            pencilMarks.add(value);
        }
    }

    public void removePencilMark(int value) {
        pencilMarks.remove(value);
    }

    public void clearPencilMarks() {
        pencilMarks.clear();
    }

    void setValue(int value) {
        validateValue(value);

        if (given && this.value != value) {
            throw new IllegalStateException("Cannot change a given cell.");
        }

        this.value = value;
        pencilMarks.clear();
    }

    void setGiven(boolean given) {
        this.given = given;
    }

    Cell copy() {
        Cell copy = new Cell(value, given);
        copy.pencilMarks.addAll(pencilMarks);
        return copy;
    }

    private static void validateValue(int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException(
                    "Sudoku values must be between 0 and 9."
            );
        }
    }

    private static void validateNumber(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException(
                    "Pencil marks must be between 1 and 9."
            );
        }
    }
}