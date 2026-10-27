# ROC Sudoku

**Team:** Bryan Steigerwald, Christian Vandenburg  
**Language:** Java  
**Interface:** Swing view (optional terminal view)

---

## 1. Project Description

ROC Sudoku is a classic Sudoku game written in Java. The player opens a Swing-based interface, selects a difficulty level, and solves a 9×9 Sudoku puzzle with support for validation, hints, and undo functionality.

The primary focus of this project is software design and implementation. The architecture emphasizes object-oriented programming principles, including:

- Coding to abstractions
- Dependency injection
- Polymorphism
- Separation of concerns

The project avoids large `if/else` chains and `switch` statements in favor of established design patterns.

### Reach Goal

Implement persistent storage using a database to track:

- Win/loss statistics
- Completion rate
- Unfinished games

There is **no `Main` class**. A small launcher class serves as the application's sole entry point, which has been approved by the instructor.

---

## 2. Design Patterns

| Pattern | Where It Is Used | Why |
|----------|------------------|-----|
| **Factory Method** | `PuzzleFactory` creates puzzles for each difficulty (`EasyPuzzleFactory`, `HardPuzzleFactory`, etc.) | Adding a new difficulty only requires adding a new factory implementation. |
| **Strategy** | `SolverStrategy` (Backtracking Solver, Constraint Propagation) and `CluePlacementStrategy` | Allows algorithms to be swapped without modifying game code. |
| **Command** *(planned)* | `Move` implements `Command` with `execute()` and `undo()`; `GameController` maintains a history stack | Provides undo/redo functionality and simplifies move logging. |
| **Observer** *(planned)* | Game publishes events (`CellChanged`, `PuzzleSolved`, `ConflictFound`) that views and statistics trackers subscribe to | Decouples the model from the view and secondary features. |

### Backup / Bonus Patterns

- Repository
- Builder
- Template Method

---

## 3. Components

### 3.1 Model

- **Board**
  - Represents the 9×9 Sudoku grid.
  - Exposes read-only views to outside consumers.

- **Cell**
  - Stores:
    - Value
    - Editable/given status
    - Optional pencil marks

- **Difficulty**
  - Enum or hierarchy that supplies puzzle generation parameters.

---

### 3.2 Puzzle Generation (Factory + Strategy)

- `PuzzleFactory` interface with one implementation per difficulty.
- Generates a complete valid Sudoku solution.
- Removes cells while maintaining a uniquely solvable puzzle.
- `CluePlacementStrategy` determines which cells are removed.

---

### 3.3 Rules and Validation

- `Rule` interface
  - `RowRule`
  - `ColumnRule`
  - `BoxRule`

- Validation operates on a `List<Rule>` to eliminate conditionals.
- Validator returns conflict information rather than throwing exceptions.

---

### 3.4 Solver / Hints

- `SolverStrategy` interface.
- Initial implementation:
  - `BacktrackingSolver`
- `HintProvider` uses the solver to reveal a correct cell.

---

### 3.5 Game Controller (Command)

- `Command` interface.
- Command implementations:
  - `PlaceNumberCommand`
  - `ClearCellCommand`

- Maintains:
  - Undo stack
  - Redo stack

- Receives dependencies through constructor injection:
  - Board
  - Validator
  - Event publisher

---

### 3.6 Events (Observer)

- `GameListener` interface.
- `EventPublisher` distributes events.

Possible event types:

- Cell Changed
- Conflict Found
- Puzzle Solved

Consumers include:

- Swing view
- Statistics tracker
- Terminal view

---

### 3.7 View

#### GameView Interface

Defines the contract for all user interfaces.

#### SwingView (Primary view)

Consists of:

- Board Panel (9×9 grid)
- Control Panel
  - Difficulty selection
  - Undo
  - Hint
- Status Bar

#### Design Requirements

- Swing components act as observers.
- view classes contain no game logic.
- Repaint based on received events.
- Button actions are mapped to `Command` objects.
- No `switch` statements in event handlers.

#### ConsoleView (Optional)

A secondary implementation of `GameView` demonstrating abstraction and interface-based design.

---

### 3.8 Persistence (Reach Goal)

#### GameRepository Interface

Initial implementation:

- `FileGameRepository`
  - JSON
  - Plain text

Future implementation:

- SQLite via JDBC

Stores:

- Unfinished games
- Win/loss statistics
- Completion history

The rest of the application interacts only with the repository abstraction.

---

### 3.9 Wiring / Dependency Injection

- No `Main.java`.
- A `GameModule` (or `GameFactory`) constructs all application dependencies.

Responsibilities include building:

- Board
- Rules
- Validator
- Solver
- Controller
- Event Publisher
- View

#### SudokuLauncher

The only entry point.

Responsibilities:

```java
SwingUtilities.invokeLater(...)
```

The launcher:

- Contains no business logic
- Simply starts the application

#### Additional Requirements

- Tests construct objects through the same module.
- No singletons.
- No static global state.

---

## 4. Suggested Package Layout

```text
app
model
factory
rules
solver
command
events
view
persistence
```

---

## 5. Work Split

| Person | Owns | Notes |
|----------|----------|----------|
| Bryan | Model, Rules/Validation, Factory, Puzzle Generation | Core game data and puzzle creation |
| Christian | Controller, Commands, Events, SwingView | Gameplay flow and view |
| Both | Solver, Hints, Module Wiring, Launcher, Testing, Persistence | Pair on interfaces first to maximize parallel development |

---

## 6. Development Milestones

### Step 1
- Agree on interfaces:
  - `Rule`
  - `SolverStrategy`
  - `PuzzleFactory`
  - `Command`
  - `GameListener`
  - `GameView`
- Create repository structure:
  - `src/main`
  - `src/test`
- Set up Git branches.

### Step 2
- Implement:
  - `Board`
  - `Cell`
  - Validation rules
- Create unit tests.

### Step 3
- Implement:
  - `BacktrackingSolver`
  - Single-difficulty puzzle factory

### Step 4
- Implement:
  - Controller
  - Commands
  - Undo/Redo
  - Event publishing
- Drive functionality through JUnit tests.

### Step 5
- Implement:
  - GameModule
  - SwingView
  - BoardPanel
  - Controls
- Add `SudokuLauncher`.

At this point the game should be fully playable.

### Step 6
- Add:
  - Additional difficulties
  - Hint support

### Step 7
- Implement persistence:
  - File storage first
  - Database storage second

### Optional
- `ConsoleView`

---

## 7. Git Branch Strategy

### Permanent Branches

```text
main
dev
```

### Feature Branches

```text
feature/model-rules
feature/factory
feature/solver
feature/commands
feature/events
feature/swing-view
feature/module-wiring
feature/persistence
feature/console-view
```

---

## 8. Rubric Checklist

- [ ] Four design patterns clearly used and documented:
  - Factory
  - Strategy
  - Command
  - Observer

- [ ] Dependency injection performed through constructors.

- [ ] Application wiring centralized in `GameModule`.

- [ ] No `Main` class.

- [ ] Only the approved `SudokuLauncher` acts as an entry point.

- [ ] Code written to interfaces and abstractions.

- [ ] No large `if/else` or `switch` chains.

- [ ] Polymorphism used wherever appropriate.

- [ ] Swing view implemented and functional.

- [ ] Persistence included as a reach goal.

---
