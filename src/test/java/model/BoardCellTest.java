package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardCellTest {

    @Test
    void boardStartsEmpty() {
        Board board = new Board();

        assertTrue(board.isEmpty(0, 0));
        assertFalse(board.isFull());
    }

    @Test
    void boardStoresValues() {
        Board board = new Board();

        board.setValue(0, 0, 5);

        assertEquals(5, board.getValue(0, 0));
        assertFalse(board.isEmpty(0, 0));
    }

    @Test
    void getValuesReturnsCopy() {
        Board board = new Board();

        board.setValue(0, 0, 5);

        int[][] values = board.getValues();
        values[0][0] = 9;

        assertEquals(5, board.getValue(0, 0));
    }

    @Test
    void copyDoesNotShareCells() {
        Board original = new Board();
        original.setValue(0, 0, 5);

        Board copy = original.copy();
        copy.setValue(0, 0, 7);

        assertEquals(5, original.getValue(0, 0));
        assertEquals(7, copy.getValue(0, 0));
    }

    @Test
    void givenCellCannotBeChanged() {
        int[][] values = new int[9][9];
        values[0][0] = 5;

        Board board = new Board(values);

        assertTrue(board.isGiven(0, 0));

        assertThrows(
                IllegalStateException.class,
                () -> board.setValue(0, 0, 7)
        );
    }

    @Test
    void editableInitialValueCanBeChanged() {
        int[][] values = new int[9][9];
        boolean[][] givens = new boolean[9][9];

        values[0][0] = 5;

        Board board = new Board(values, givens);

        board.setValue(0, 0, 7);

        assertEquals(7, board.getValue(0, 0));
        assertFalse(board.isGiven(0, 0));
    }

    @Test
    void markAsGivenLocksCell() {
        Board board = new Board();
        board.setValue(0, 0, 4);

        board.markAsGiven(0, 0);

        assertTrue(board.isGiven(0, 0));

        assertThrows(
                IllegalStateException.class,
                () -> board.setValue(0, 0, 6)
        );
    }
}