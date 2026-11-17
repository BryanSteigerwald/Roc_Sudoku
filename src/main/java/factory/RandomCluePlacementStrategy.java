package factory;

import model.Board;
import model.CellPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomCluePlacementStrategy implements CluePlacementStrategy {

    private final Random random;

    public RandomCluePlacementStrategy() {
        this(new Random());
    }

    public RandomCluePlacementStrategy(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("Random cannot be null.");
        }

        this.random = random;
    }

    @Override
    public List<CellPosition> removalOrder() {
        List<CellPosition> positions = new ArrayList<>();

        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                positions.add(new CellPosition(row, col));
            }
        }

        Collections.shuffle(positions, random);
        return positions;
    }
}