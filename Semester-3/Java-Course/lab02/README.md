# Lab 02: Ring Problem-Solving Algorithm

## 📖 Project Description
This project solves an problem regarding covering circular holes in a plate using stacks of rings. The program reads data regarding the holes (in a plate) and available rings, then calculates the optimal stacking configurations based on specific criteria.

### 🛠 Core Technologies & Concepts
* **Maven:** Used for project build automation and dependency management via `pom.xml`.
* **Recursion & Backtracking:** The core algorithmic approach used to explore all valid ring combinations and find optimal stacks.

## 🎯 The Task
The goal is to cover holes in a plate by arranging stacks of rings over them. During the construction of these stacks, the program must **minimize** or **maximize** two specific values:
1.  **H (Height):** The total height of the ring stack.
2.  **C (Count):** The number of rings used in the stack.

### 🧩 Stacking Logic
Rings are not merely piled up, they must satisfy specific geometric conditions to form a valid stable stack that "closes" the hole:

1.  **Placement on Plate:** A ring can be placed directly on the plate if the plate's hole radius is smaller than the ring's outer radius (so it doesn't fall through) and larger than the ring's inner radius.
2.  **Stacking Rings:** A new ring (`next`) can be placed on top of an existing ring (`prev`) only if:
    * `prev.innerRadius < next.outerRadius`: The new ring is wide enough to sit on the previous ring.
    * `prev.innerRadius > next.innerRadius`: The new ring has a smaller hole than the previous one, effectively narrowing the opening.

### ⚖️ Optimization Criteria
The program searches for solutions that satisfy one of the four possible optimization strategies:
1.  **(min H, min C):** Minimize total height, minimize number of rings.
2.  **(min H, max C):** Minimize total height, maximize number of rings.
3.  **(max H, min C):** Maximize total height, minimize number of rings.
4.  **(max H, max C):** Maximize total height, maximize number of rings.

---

## 📥 Input Data Format
The program requires two input text files (CSV-like format). Lines starting with `#` are treated as comments.

### 1. Plate File (`plates.txt`)
Contains the definition of holes in the base plate.
**Format:** `id, radius`

Example:
```text
# id, radius
1, 15.0
2, 30.5
3, 10.0
```

### 2. Rings File (`rings.txt`)
Contains the available inventory of rings. A ring with an inner radius of `0` is considered a solid disk (circle).
**Format:** `id, outer_radius, inner_radius, height`

Example:
```text
# id, outer_radius, inner_radius, height
1, 13.0, 10.0, 3.0
2, 15.0, 9.0, 4.5
3, 12.0, 0.0, 2.0
```

---

## 🚀 How to Run

### ⚙️ Prerequisites
To run the projects in this repository, you will need:

* **JDK 17** or higher
* **IntelliJ IDEA** (recommended) or Eclipse

### 🔨 Compilation
Navigate to the root `src/main/java` directory of your project and compile the Java files:

```bash
javac org/padadak/Main.java
```

### ▶️ Execution
Run the main class:

```bash
java org.padadak.Main
```

### 🖥️ Usage
Upon running, the program will prompt you to enter the paths to your data files via the console.

**Example Interaction:**
```text
Enter the plate file path:
data/plyta.txt
Enter the rings file path:
data/pierscienie.txt
```

---
## 📂 Project Structure
The project is organized under the root package `org.padadak`.

### 📂 Source Code
* **`org.padadak`**
    * `Main.java`: Entry point.
    * **`algorithm`**
        * `Calculator.java`: Optimization logic.
    * **`input`**
        * `ConsoleManager.java`: User interface logic.
        * `FileReader.java`: File parsing logic.
    * **`model`**
        * `Plate.java`: Data model for plate.
        * `Ring.java`: Data model for ring.

### 📂 Data
* **`data-example/`**: Directory containing sample input files (`plates.txt`, `rings.txt`).
---
*Return to [Main Repository](../)*
