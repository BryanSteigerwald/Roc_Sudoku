package factory;

public enum Difficulty {
    EASY(40),
    MEDIUM(34),
    HARD(29),
    EXPERT(25);

    private final int targetClues;

    Difficulty(int targetClues) {
        this.targetClues = targetClues;
    }

    public int getTargetClues() {
        return targetClues;
    }
}