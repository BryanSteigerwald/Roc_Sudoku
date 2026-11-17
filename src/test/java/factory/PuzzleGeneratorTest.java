package factory;

import model.Board;
import org.junit.jupiter.api.Test;
import rules.SudokuValidator;
import solver.BacktrackingSolver;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleGeneratorTest {

    private PuzzleGenerator createGenerator() {
        SudokuValidator validator = new SudokuValidator();
        BacktrackingSolver solver = new BacktrackingSolver(validator);

        return new PuzzleGenerator(
                solver,
                new RandomCluePlacementStrategy()
        );
    }

    @Test
    void generatedPuzzleIsValid() {
        PuzzleGenerator generator = createGenerator();
        SudokuValidator validator = new SudokuValidator();

        Board puzzle = generator.generate(Difficulty.EASY);

        assertNotNull(puzzle);
        assertTrue(validator.isValidBoard(puzzle));
    }

    @Test
    void generatedPuzzleIsNotCompletelyFilled() {
        PuzzleGenerator generator = createGenerator();

        Board puzzle = generator.generate(Difficulty.EASY);

        assertFalse(puzzle.isFull());
    }

    @Test
    void generatedPuzzleHasUniqueSolution() {
        SudokuValidator validator = new SudokuValidator();
        BacktrackingSolver solver = new BacktrackingSolver(validator);
        PuzzleGenerator generator = new PuzzleGenerator(
                solver,
                new RandomCluePlacementStrategy()
        );

        Board puzzle = generator.generate(Difficulty.EASY);

        assertEquals(1, solver.countSolutions(puzzle, 2));
    }

    @Test
    void generatedCluesAreMarkedAsGiven() {
        PuzzleGenerator generator = createGenerator();

        Board puzzle = generator.generate(Difficulty.EASY);

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (!puzzle.isEmpty(row, col)) {
                    assertTrue(puzzle.isGiven(row, col));
                }
            }
        }
    }

    @Test
    void emptyCellsRemainEditable() {
        PuzzleGenerator generator = createGenerator();

        Board puzzle = generator.generate(Difficulty.EASY);

        boolean foundEmptyCell = false;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (puzzle.isEmpty(row, col)) {
                    foundEmptyCell = true;

                    puzzle.setValue(row, col, 1);
                    assertEquals(1, puzzle.getValue(row, col));

                    return;
                }
            }
        }

        assertTrue(foundEmptyCell);
    }

    @Test
    void easyPuzzleHasAtLeastTargetNumberOfClues() {
        PuzzleGenerator generator = createGenerator();

        Board puzzle = generator.generate(Difficulty.EASY);

        int clueCount = 0;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (!puzzle.isEmpty(row, col)) {
                    clueCount++;
                }
            }
        }

        assertTrue(clueCount >= Difficulty.EASY.getTargetClues());
    }
}