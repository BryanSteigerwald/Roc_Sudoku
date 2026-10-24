
package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void newBoardStartsEmpty() {
        Board board = new Board();

        assertEquals(Board.EMPTY, board.getValue(0, 0));
        assertFalse(board.isFull());
    }

    @Test
    void setAndGetValue() {
        Board board = new Board();

        board.setValue(0, 0, 5);

        assertEquals(5, board.getValue(0, 0));
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
    void rejectsValuesOutsideRange() {
        Board board = new Board();

        assertThrows(
                IllegalArgumentException.class,
                () -> board.setValue(0, 0, 10)
        );
    }
}
