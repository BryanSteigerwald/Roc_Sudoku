package factory;

import model.Board;
import org.junit.jupiter.api.Test;
import rules.SudokuValidator;
import solver.BacktrackingSolver;

import static org.junit.jupiter.api.Assertions.*;

class GeneratedPuzzleFactoryTest {

    @Test
    void factoryCreatesPuzzle() {
        SudokuValidator validator = new SudokuValidator();
        BacktrackingSolver solver = new BacktrackingSolver(validator);

        PuzzleGenerator generator = new PuzzleGenerator(
                solver,
                new RandomCluePlacementStrategy()
        );

        PuzzleFactory factory = new GeneratedPuzzleFactory(
                generator,
                Difficulty.EASY
        );

        Board puzzle = factory.createPuzzle();

        assertNotNull(puzzle);
        assertTrue(validator.isValidBoard(puzzle));
    }
}