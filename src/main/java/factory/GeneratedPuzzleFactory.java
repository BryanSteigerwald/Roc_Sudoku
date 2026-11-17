package factory;

import model.Board;

public class GeneratedPuzzleFactory extends PuzzleFactory {

    private final PuzzleGenerator generator;
    private final Difficulty difficulty;

    public GeneratedPuzzleFactory(
            PuzzleGenerator generator,
            Difficulty difficulty
    ) {
        if (generator == null || difficulty == null) {
            throw new IllegalArgumentException(
                    "Generator and difficulty cannot be null."
            );
        }

        this.generator = generator;
        this.difficulty = difficulty;
    }

    @Override
    public Board createPuzzle() {
        return generator.generate(difficulty);
    }
}