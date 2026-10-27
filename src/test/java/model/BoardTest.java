package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testDefaultBoardIsEmpty() {
        Board board = new Board();

        assertTrue(board.isEmpty(0, 0));
        assertFalse(board.isFull());
    }

    @Test
    void testSetAndGetValue() {
        Board board = new Board();

        board.setValue(3, 4, 7);

        assertEquals(7, board.getValue(3, 4));
    }

    @Test
    void testInvalidRowCountThrowsException() {
        int[][] badBoard = new int[8][9];

        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(badBoard)
        );
    }

    @Test
    void testInvalidCellValueThrowsException() {
        int[][] values = new int[9][9];
        values[0][0] = 10;

        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(values)
        );
    }

    @Test
    void testInvalidCoordinatesThrowException() {
        Board board = new Board();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> board.getValue(9, 0)
        );
    }

    @Test
    void testGetValuesReturnsDefensiveCopy() {
        Board board = new Board();

        int[][] copy = board.getValues();
        copy[0][0] = 9;

        assertEquals(0, board.getValue(0, 0));
    }

    @Test
    void testBoardIsFull() {
        int[][] values = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                values[row][col] = 1;
            }
        }

        Board board = new Board(values);

        assertTrue(board.isFull());
    }
}