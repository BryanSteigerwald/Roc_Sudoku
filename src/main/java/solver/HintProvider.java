package solver;

import model.Board;

import java.util.Optional;

public interface HintProvider {

    Optional<Hint> getHint(Board board);
}