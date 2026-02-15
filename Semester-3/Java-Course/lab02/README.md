# Lab 02: RingStacker Optimizer
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
## 📖 Project Description
This project solves an problem regarding covering circular holes in a plate using stacks of rings. The program reads data regarding the holes (in a plate) and available rings, then calculates the optimal stacking configurations based on specific criteria.

### 🛠 Technical Stack & Concepts

* **Build Automation (Maven):** Manages dependencies and automates the build process. This ensures the project is portable and can be easily compiled or run on any machine with a single command.
* **Smart Search (Recursion & Backtracking):** The core algorithm used to find the best possible solutions. It efficiently explores different combinations and "steps back" when a path is invalid, ensuring an optimal result without checking every unnecessary option.
* **Modular Project Structure:** The code is organized into distinct layers (Models, Logic, and I/O). This separation of concerns follows clean code principles, making the project easy to read, test, and maintain.

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
* JDK 17 or newer
* Maven 3.6+ (optional but recommended)

### 🔨 Build (using Maven)
From the project root (`lab02`) run:

```powershell
mvn clean package
```

### ▶️ Run
Once the build is successful, run the application from the project root:

```powershell
java -jar target/ring-optimizer.jar
```

The program will prompt you to enter paths to the plate and rings files (you can use `data-example/plates.txt` and `data-example/rings.txt`).

---

### 📋 Sample Output
After providing the file paths, the program will display the calculated optimal stacks for each hole.  
**Example output in the console:**
```text
Enter the plate file path: 
data-example/plates.txt
Enter the rings file path:
data-example/rings.txt
Plate: 1
(min H, min C) H = 3.0, C = 1
Rings: 1
(min H, max C) H = 3.0, C = 1
Rings: 1
(max H, min C) H = 7.0, C = 3
Rings: 2 16 1
(max H, max C) H = 7.0, C = 3
Rings: 2 16 1


Plate: 2
(min H, min C) H = 5.0, C = 2
Rings: 16 1
(min H, max C) H = 5.0, C = 2
Rings: 16 1
(max H, min C) H = 16.0, C = 5
Rings: 4 3 2 16 1
(max H, max C) H = 16.0, C = 5
Rings: 4 3 2 16 1
```

---

## 📂 Project Structure
The project is organized under the root package `org.padadak`. Minimal, focused tree (matches local layout):

```
lab02/
├── pom.xml
├── README.md
├── data-example/
│   ├── plates.txt
│   └── rings.txt
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── module-info.java
│   │   │   └── org/padadak/
│   │   │       ├── Main.java
│   │   │       ├── algorithm/
│   │   │       │   └── Calculator.java
│   │   │       ├── input/
│   │   │       │   ├── ConsoleManager.java
│   │   │       │   └── FileReader.java
│   │   │       └── model/
│   │   │           ├── Plate.java
│   │   │           └── Ring.java
│   │   └── resources/
│   └── test/
│       └── java/
└── target/ (build output)
```

---
*Return to [Main Repository](../)*
