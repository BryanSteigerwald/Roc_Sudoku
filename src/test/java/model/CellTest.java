package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void newCellIsEmptyAndNotGiven() {
        Cell cell = new Cell();

        assertEquals(0, cell.getValue());
        assertTrue(cell.isEmpty());
        assertFalse(cell.isGiven());
    }

    @Test
    void cellStoresValue() {
        Cell cell = new Cell();

        cell.setValue(5);

        assertEquals(5, cell.getValue());
        assertFalse(cell.isEmpty());
    }

    @Test
    void givenCellCannotBeChanged() {
        Cell cell = new Cell(5, true);

        assertThrows(
                IllegalStateException.class,
                () -> cell.setValue(6)
        );
    }

    @Test
    void pencilMarksCanBeAddedAndRemoved() {
        Cell cell = new Cell();

        cell.addPencilMark(3);
        cell.addPencilMark(7);

        assertTrue(cell.getPencilMarks().contains(3));
        assertTrue(cell.getPencilMarks().contains(7));

        cell.removePencilMark(3);

        assertFalse(cell.getPencilMarks().contains(3));
        assertTrue(cell.getPencilMarks().contains(7));
    }

    @Test
    void pencilMarksAreClearedWhenValueIsPlaced() {
        Cell cell = new Cell();

        cell.addPencilMark(2);
        cell.addPencilMark(8);
        cell.setValue(2);

        assertTrue(cell.getPencilMarks().isEmpty());
    }

    @Test
    void invalidValuesAreRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Cell(10, false)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Cell(-1, false)
        );
    }
}