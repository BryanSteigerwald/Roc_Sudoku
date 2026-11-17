package solver;

import model.Board;
import rules.SudokuValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BacktrackingSolver implements SolverStrategy {

    private final SudokuValidator validator;
    private final Random random;

    public BacktrackingSolver(SudokuValidator validator) {
        this(validator, new Random());
    }

    public BacktrackingSolver(
            SudokuValidator validator,
            Random random
    ) {
        if (validator == null || random == null) {
            throw new IllegalArgumentException(
                    "Solver dependencies cannot be null."
            );
        }

        this.validator = validator;
        this.random = random;
    }

    @Override
    public boolean solve(Board board) {
        if (!isValidPartialBoard(board)) {
            return false;
        }

        return solveRecursive(board);
    }

    public boolean isValidPartialBoard(Board board) {
        return board != null && validator.isValidBoard(board);
    }

    public int countSolutions(Board board, int limit) {
        if (board == null || limit < 1
                || !validator.isValidBoard(board)) {
            return 0;
        }

        return countSolutionsRecursive(board.copy(), limit);
    }

    private boolean solveRecursive(Board board) {
        int[] position = findBestEmptyCell(board);

        if (position == null) {
            return true;
        }

        int row = position[0];
        int col = position[1];

        List<Integer> candidates = getCandidates(board, row, col);
        Collections.shuffle(candidates, random);

        for (int candidate : candidates) {
            board.setValue(row, col, candidate);

            if (solveRecursive(board)) {
                return true;
            }

            board.setValue(row, col, Board.EMPTY);
        }

        return false;
    }

    private int countSolutionsRecursive(Board board, int limit) {
        int[] position = findBestEmptyCell(board);

        if (position == null) {
            return 1;
        }

        int row = position[0];
        int col = position[1];
        int count = 0;

        for (int candidate : getCandidates(board, row, col)) {
            board.setValue(row, col, candidate);

            count += countSolutionsRecursive(
                    board,
                    limit - count
            );

            board.setValue(row, col, Board.EMPTY);

            if (count >= limit) {
                return count;
            }
        }

        return count;
    }

    private int[] findBestEmptyCell(Board board) {
        int[] bestPosition = null;
        int fewestCandidates = 10;

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (!board.isEmpty(row, col)) {
                    continue;
                }

                int candidateCount =
                        getCandidates(board, row, col).size();

                if (candidateCount < fewestCandidates) {
                    fewestCandidates = candidateCount;
                    bestPosition = new int[]{row, col};

                    if (fewestCandidates <= 1) {
                        return bestPosition;
                    }
                }
            }
        }

        return bestPosition;
    }

    private List<Integer> getCandidates(
            Board board,
            int row,
            int col
    ) {
        List<Integer> candidates = new ArrayList<>();

        for (int value = 1; value <= 9; value++) {
            board.setValue(row, col, value);

            if (validator.isValidBoard(board)) {
                candidates.add(value);
            }

            board.setValue(row, col, Board.EMPTY);
        }

        return candidates;
    }
}