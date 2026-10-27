package factory;

import model.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleFactoryTest {

    @Test
    void testCreatePuzzleReturnsBoard() {
        EasyPuzzleFactory factory = new EasyPuzzleFactory();

        Board board = factory.createPuzzle();

        assertNotNull(board);
    }

    @Test
    void testCreatePuzzleContainsExpectedValues() {
        EasyPuzzleFactory factory = new EasyPuzzleFactory();

        Board board = factory.createPuzzle();

        assertEquals(5, board.getValue(0, 0));
        assertEquals(3, board.getValue(0, 1));
        assertEquals(7, board.getValue(0, 4));
        assertEquals(0, board.getValue(0, 2));
    }

    @Test
    void testCreatePuzzleReturnsNewBoardInstance() {
        EasyPuzzleFactory factory = new EasyPuzzleFactory();

        Board first = factory.createPuzzle();
        Board second = factory.createPuzzle();

        assertNotSame(first, second);
    }
}