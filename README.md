ROC_Sudoku
Team: Bryan Steigerwald, Christian Vandenburg   |   Language: Java   |   Interface: Swing UI (terminal view optional)
1. Project Description
ROC_Sudoku is a classic Sudoku game written in Java. The player opens a Swing window, picks a difficulty, and fills in a 9x9 grid with validation, hints, and undo. The code is the focus of the grade, so the design emphasizes OO principles: coding to abstractions, dependency injection, and polymorphism instead of big if/else or switch blocks.
Reach goal: a database that stores win rate and unfinished games. There is no Main class: the only entry point is a tiny launcher for the Swing UI, which the prof has approved.
2. Design Patterns (4 required)
Pattern
Where it is used
Why
Factory Method
PuzzleFactory creates puzzles per difficulty (EasyPuzzleFactory, HardPuzzleFactory, ...)
Game code never uses new on a concrete puzzle type. Adding a difficulty means adding a class.
Strategy
SolverStrategy (backtracking, constraint propagation) and CluePlacementStrategy
Swap algorithms without touching the game. Lets us use different solvers for hints vs. validation.
Command(not learned yet)
Move implements Command with execute() and undo(); GameController keeps a history stack
Gives undo/redo with no conditionals. Moves become easy to log and persist.
Observer (not learned yet)
Game publishes events (CellChanged, PuzzleSolved, ConflictFound); views and stats tracker subscribe
Decouples the model from the UI: Swing panels (and an optional console view) just subscribe.


Backup/bonus candidates: Repository (persistence), Builder (board construction), Template Method (shared rendering skeleton).
3. Components
3.1 Model
Board: 9x9 grid of Cell objects, exposes read-only views to everything outside the model.
Cell: value, given/editable flag, optional pencil marks.
Difficulty: enum or class hierarchy that supplies clue counts to the factory.
3.2 Puzzle Generation (Factory + Strategy)
PuzzleFactory interface with one implementation per difficulty.
Generate a full valid solution, then remove cells while the puzzle stays uniquely solvable.
CluePlacementStrategy decides which cells to remove.
3.3 Rules and Validation
Rule interface: RowRule, ColumnRule, BoxRule. Board validation loops over a List<Rule>, so no switch statements.
Validator returns a list of conflicts instead of throwing.
3.4 Solver / Hints
SolverStrategy interface with at least a BacktrackingSolver.
HintProvider uses the solver to reveal one correct cell.
3.5 Game Controller (Command)
Command interface; PlaceNumberCommand, ClearCellCommand.
Undo/redo stacks. Controller takes the board, validator, and event bus through its constructor.
3.6 Events (Observer)
GameListener interface and a simple EventPublisher.
Terminal view and stats tracker are both listeners.
3.7 View
GameView interface. SwingView is the primary view: a BoardPanel (9x9 grid), a control panel (difficulty, undo, hint), and a status bar.
Swing panels are listeners (Observer): they repaint when they receive CellChanged, ConflictFound, or PuzzleSolved events. No game logic inside Swing classes.
Button and key actions are mapped to Command objects, so there are no switch statements in event handlers.
ConsoleView is optional and only worth adding if time allows. It also makes a good second GameView to show the abstraction works.
3.8 Persistence (reach goal)
GameRepository interface with FileGameRepository first (JSON or plain text), then SQLite/JDBC.
Stores unfinished games and a win/loss record. The rest of the code only sees the interface.
3.9 Wiring / Dependency Injection (no Main)
No Main.java. A plain GameModule (or GameFactory) class builds the board, rules, validator, solver, controller, event publisher, and view, and passes everything in through constructors.
SudokuLauncher is the only entry point: a tiny class whose only job is SwingUtilities.invokeLater(...) to start a GameApp built by GameModule. It contains no logic. It is the one launcher the prof approved.
JUnit tests also build the game through GameModule, so the same wiring is used by tests and the UI.
No singletons and no static global state.
4. Suggested Package Layout
model, factory, rules, solver, command, events, view, persistence, app
5. Work Split (suggestion)
Person
Owns
Notes
Bryan
Model, rules/validation, factory + generation
Core data and puzzle creation
Christian
Controller + commands, events, SwingView
Gameplay loop and UI
Both
Solver/hints, GameModule wiring, launcher, tests, persistence
Pair on interfaces first so you can work in parallel

6. Where to Start (milestones)
Step 1: Agree on interfaces together (Rule, SolverStrategy, PuzzleFactory, Command, GameListener, GameView). Set up the repo with src/main and src/test, and branches.
Step 2: Board and Cell plus rules/validation, with unit tests.
Step 3: BacktrackingSolver, then PuzzleFactory with a single difficulty.
Step 4: Game controller with Command and undo, plus event publishing. Test it by driving the controller from JUnit.
Step 5: GameModule wiring plus SwingView (BoardPanel first, then controls). Add SudokuLauncher last. At this point the game is playable end to end.
Step 6: More difficulties and hints.
Step 7: Reach goal: persistence (file first, then database).
Optional: ConsoleView.
7. Git Branches
main (always runnable), dev (integration)
feature/model-rules, feature/factory, feature/solver, feature/commands, feature/events, feature/swing-view, feature/module-wiring, feature/persistence, feature/console-view (optional)
8. Rubric Checklist
Four patterns used and clearly identified in comments/README: Factory, Strategy, Command, Observer.
Dependency injection through constructors; everything created in GameModule.
No Main class; only the tiny approved launcher with zero logic.
Code to interfaces; no concrete types in field declarations where an abstraction exists.
No large if/else or switch chains; use polymorphism and maps of handlers.
UI requirement met by SwingView; persisted state as a reach goal.
