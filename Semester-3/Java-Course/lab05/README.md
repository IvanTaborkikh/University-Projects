# Multithreaded Board Simulator
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Concurrency](https://img.shields.io/badge/Concurrency_&_Multithreading-007396?style=for-the-badge&logo=java&logoColor=white)

## 📖 Project Description
This project implements a parameterized multithreaded simulator in which autonomous agents ("figures") move and interact on a shared rectangular board. Each figure runs in its own thread and the application demonstrates synchronization, safe resource access, and configurable generation of actors and treasures.

### 🛠 Technical Stack & Concepts
- Build Automation: Maven — portable build lifecycle and reproducible packaging (artifact produced via `mvn clean package`).
- Core Concept: Multithreading & Synchronization — thread lifecycle management, safe shared-state access, coordination between a Creator thread and per-figure threads.


---

## 🎯 The Task
Implement a concurrent simulation in which multiple threads represent different types of Figures moving on a rectangular board. A dedicated Creator thread generates Figures and buried Treasures at random, subject to configurable parameters (generation frequencies, board area, etc.). Each Figure has a facing direction (8 orientations: up/down/left/right and the four diagonals), may rotate by 45° increments, and acts according to its concrete type. The board is a shared resource: no two Figures may occupy the same cell simultaneously. Proper synchronization must prevent race conditions, avoid deadlocks, and respect the rules below for interaction and propagation.

### 🧩 Implementation Logic

**Execution Model**
- Each Figure runs on a dedicated thread with a 500 ms cycle.
- Creator runs on a separate thread with a 1000 ms cycle.
- Render runs on a third thread, refreshing the board view every 700 ms.

**Creator Behavior**
- Randomly selects a cell on the board each cycle.
- 30% chance: Attempts to place a Treasure (occupies the cell for ~700 ms).
- 70% chance: Attempts to spawn a Figure (Shooter, Pusher, or Searcher) respecting max counts.
- While operating on a cell, the Creator marks it with flag `setCreator(true)`, blocking other operations.
- Treasures and Figures are only placed on free (unoccupied) cells.

**Board & Cells**
- Rectangular grid of synchronized Cells. Each Cell tracks:
  - Occupying Figure (if any)
  - Treasure presence
  - Creator occupation flag
  - Shot propagation flag
  - Dead marker (for visualization)
- Board operations (`move`, `placeFigure`, `collectTreasure`) are synchronized to prevent race conditions.

**Figure Common Behavior**
- Each Figure is a `Runnable` managed by its own thread.
- Base movement: random step in a 3×3 neighborhood (moveRandom).
- Figures **cannot** move into:
  - Cells with another Figure
  - Cells currently occupied by Creator
  - (For non-Searchers) cells with Treasures
- Base cycle: move, sleep 500 ms, repeat until alive=false.

**Shooter**
- Displays symbol `S`.
- Each cycle: randomly selects a direction, fires a shot in that direction for ~2 seconds, then clears it.
- Shot mechanism: marks 3 cells ahead (in direction) with `setShoot(true)`.
- Hit logic: if a non-Pusher Figure occupies cells 1, 2, or 3 in firing direction:
  - Figure is hit (`setAlive(false)`) and removed.
  - Shot stops propagating (does not reach further cells).
- Pusher is immune: shot passes through without removing it.
- Shots do not propagate through Creator-occupied cells.

**Pusher**
- Displays symbol `P`.
- **Pushing Mechanic**: Unlike other figures, the Pusher can move into occupied cells by shifting the existing occupant.
- **Interaction Logic**: When moving, if the target cell is occupied by another Figure (Shooter or Searcher), the Pusher attempts to "push" that figure one cell further in the same direction of travel.
- **Synchronization & Constraints**:
  - Pushing only succeeds if the next cell in the chain is empty and not occupied by the Creator.
  - If the push is successful, both figures change their positions atomically within one synchronized cycle.
  - If the space behind the target figure is blocked, the Pusher's move is cancelled (it remains in place).

**Searcher**
- Displays symbol `C`.
- Enhanced movement: moves toward nearest Treasure (if one exists) using Manhattan distance.
- If no Treasure exists, falls back to random movement.
- Treasure collection: when Searcher occupies a cell with Treasure, it collects it (counter increments).
- Upgrade: upon collecting 10 Treasures, Searcher transforms into a Shooter:
  - Current Searcher thread exits.
  - New Shooter is spawned at the same location.
  - Shooter thread starts independently.

**Visualization & Rendering**
- Separate Render thread redraws the board every 700 ms.
- Renders Figures by their symbols, Creator as `K`, Treasures as `T`, shots as `*`, empty as `.`.
- The 700 ms refresh may skip intermediate events (acceptable "visual jumps").

### ⚖️ Optimization/Process Criteria
- Thread-safety: use fine-grained locking (per-cell locks or a Lock matrix) to increase concurrency and reduce unnecessary blocking.
- Deadlock avoidance: adopt lock ordering or use try-lock with backoff to prevent cycles when multiple cells must be claimed atomically (e.g., pushes or shot propagation).
- Fairness and responsiveness: avoid starvation by tuning thread scheduling and generation rates; consider bounded queues or semaphores to limit simultaneous Figures.
- Determinism for testing: support an optional random seed to reproduce scenarios.
- Efficiency: minimize global synchronization; prefer local cell-level synchronization and concurrent collections where appropriate.

## 📥 Input Data Format
The application uses interactive console input via `Scanner`. On startup, the program prompts for the following parameters:

```
Max Shooters: <int>
Max Pushers: <int>
Max Searchers: <int>
Width: <int>
Height: <int>
```

**Parameters:**
- **Max Shooters** — maximum number of Shooter figures that Creator can spawn (0–N).
- **Max Pushers** — maximum number of Pusher figures that Creator can spawn (0–N).
- **Max Searchers** — maximum number of Searcher figures that Creator can spawn (0–N).
- **Width** — board width in cells (typically 10–20).
- **Height** — board height in cells (typically 10–20).

**Example input:**
```
Max Shooters: 5
Max Pushers: 5
Max Searchers: 5
Width: 10
Height: 10
```

## 🚀 How to Run
### ⚙️ Prerequisites
* Java 21 or higher
* Maven 3.8+

### 🔨 Build
```
mvn clean package
```
This command compiles source code, runs tests (if available), and packages the project into an executable JAR file.

### ▶️ Run
Once the build is successful, run the application from the project root:
```bash
java -jar target/treasure-simulation.jar
```

The program will prompt you to enter some parameters for the simulation. For example, you can enter:
```
Max Shooters: 5
Max Pushers: 5
Max Searchers: 5
Width: 10
Height: 10
```

After entering the parameters, the simulation starts and renders the board state every 700 ms while logging events to the console.

### 📋 Sample Output
A representative console output from a simulation run with 10×10 board:

```
Max Shooters: 5
Max Pushers: 5
Max Searchers: 5
Width: 10
Height: 10



. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 



. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. K . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
[Creator] Treasure at 1,5
Pusher started
[Creator] Created Pusher at 1,5



. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. P . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
[Creator] Created Pusher at 8,4
Pusher started



. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. P . . . . . . P . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
[Creator] Created Searcher at 0,2
Searcher started



. . . . . . . . . . 
. . . . . . . . . . 
. C . . . . . . . . 
. . . . K . . . . . 
. . . . . . . . . . 
. . . P . . . . . . 
. . . . . . . . P . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
[Creator] Created Shooter at 4,3
Shooter started



. . . . . . . . . . 
. C . . . . . . . . 
. . . . . . . . . . 
. . . . S * * * . . 
. . . . . . . . . . 
. . . P . . . . . . 
. . . . . . . . P . 
. . . . . . . . . . 
. . . . . . . . . . 
. . . . . . . . . . 
[Creator] Created Shooter at 5,9
Shooter started
```

**Legend:**
- `.` — Empty cell
- `K` — Creator (occupied during treasure/figure placement)
- `T` — Treasure (collectible by Searchers)
- `P` — Pusher figure
- `C` — Searcher figure
- `S` — Shooter figure
- `*` — Active shot projectile
- `0` — Dead figure (briefly displayed before removal)

## 📂 Project Structure
```
lab05/
├── pom.xml                                    # Maven project configuration
├── README.md                                  # This documentation file
│
├── src/
│   └── main/
│       ├── java/
│       │   └── org/padadak/
│       │       ├── Main.java                 # Application entry point & input controller
│       │       ├── Render.java               # Console board rendering thread
│       │       └── objects/
│       │           ├── Board.java            # Shared board state & synchronization
│       │           ├── Cell.java             # Grid cell state (occupant, treasure, etc.)
│       │           ├── Creator.java          # Figure & treasure generation thread
│       │           ├── Direction.java        # 8-directional enum (UP, DOWN, etc.)
│       │           ├── Figure.java           # Abstract base class for all figures
│       │           ├── Shooter.java          # Figure type: shoots other figures
│       │           ├── Pusher.java           # Figure type: pushes other figures
│       │           └── Searcher.java         # Figure type: seeks treasures, upgrades to Shooter
│       └── resources/
│
└── target/
    ├── classes/                              # Compiled .class files
    └── generated-sources/                    # Generated source annotations
```
---

*Return to [Main Repository](../)*