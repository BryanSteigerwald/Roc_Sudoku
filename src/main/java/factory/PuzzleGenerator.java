package factory;

import model.Board;
import model.CellPosition;
import solver.BacktrackingSolver;

import java.util.List;
import java.util.Random;

public class PuzzleGenerator {

    private final BacktrackingSolver solver;
    private final CluePlacementStrategy clueStrategy;
    private final Random random;

    public PuzzleGenerator(
            BacktrackingSolver solver,
            CluePlacementStrategy clueStrategy
    ) {
        this(solver, clueStrategy, new Random());
    }

    public PuzzleGenerator(
            BacktrackingSolver solver,
            CluePlacementStrategy clueStrategy,
            Random random
    ) {
        if (solver == null || clueStrategy == null || random == null) {
            throw new IllegalArgumentException(
                    "Generator dependencies cannot be null."
            );
        }

        this.solver = solver;
        this.clueStrategy = clueStrategy;
        this.random = random;
    }

    public Board generate(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException(
                    "Difficulty cannot be null."
            );
        }

        Board solution = generateCompleteBoard();

        Board puzzle = new Board(
                solution.getValues(),
                new boolean[Board.SIZE][Board.SIZE]
        );

        List<CellPosition> removalOrder =
                clueStrategy.removalOrder();

        if (removalOrder == null) {
            throw new IllegalStateException(
                    "Clue strategy returned a null removal order."
            );
        }

        int clueCount = Board.SIZE * Board.SIZE;
        int targetClues = difficulty.getTargetClues();

        for (CellPosition position : removalOrder) {
            if (clueCount <= targetClues) {
                break;
            }

            int row = position.row();
            int col = position.col();
            int previousValue = puzzle.getValue(row, col);

            if (previousValue == Board.EMPTY) {
                continue;
            }

            puzzle.setValue(row, col, Board.EMPTY);

            if (solver.countSolutions(puzzle, 2) == 1) {
                clueCount--;
            } else {
                puzzle.setValue(row, col, previousValue);
            }
        }

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (!puzzle.isEmpty(row, col)) {
                    puzzle.markAsGiven(row, col);
                }
            }
        }

        return puzzle;
    }

    private Board generateCompleteBoard() {
        Board board = new Board();

        if (!fillRandomly(board)) {
            throw new IllegalStateException(
                    "Could not generate a complete Sudoku board."
            );
        }

        return board;
    }

    private boolean fillRandomly(Board board) {
        int[] position = findFirstEmptyCell(board);

        if (position == null) {
            return true;
        }

        int row = position[0];
        int col = position[1];

        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffle(values);

        for (int value : values) {
            board.setValue(row, col, value);

            if (solver.isValidPartialBoard(board)
                    && fillRandomly(board)) {
                return true;
            }

            board.setValue(row, col, Board.EMPTY);
        }

        return false;
    }

    private int[] findFirstEmptyCell(Board board) {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.isEmpty(row, col)) {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    private void shuffle(int[] values) {
        for (int i = values.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int temp = values[i];
            values[i] = values[j];
            values[j] = temp;
        }
    }
}