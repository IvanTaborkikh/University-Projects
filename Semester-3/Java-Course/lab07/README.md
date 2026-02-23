# 🌊 Distributed River Network Simulator — Java RMI

![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white&labelColor=C71A36)
![Java 25](https://img.shields.io/badge/Java_25-ED8B00?style=for-the-badge&logo=java&logoColor=white&labelColor=ED8B00)
![RMI](https://img.shields.io/badge/Protocol-Java_RMI-6f42c1?style=for-the-badge&logo=java&logoColor=white&labelColor=6f42c1)
![JavaFX](https://img.shields.io/badge/GUI-JavaFX-007396?style=for-the-badge&logo=java&logoColor=white&labelColor=007396)
![Distributed](https://img.shields.io/badge/Architecture-Distributed-28a745?style=for-the-badge&labelColor=28a745)
![Tailor](https://img.shields.io/badge/Registry-Tailor_Service-FF5722?style=for-the-badge&logo=lighthouse&logoColor=white&labelColor=FF5722)

---

## 📖 Project Description

A distributed system that simulates a river network with retention basins, environmental rainfall, and a centralized control panel — all communicating via **Java RMI** (Remote Method Invocation). Each subsystem runs as an independent JavaFX application, registers itself through a custom service-discovery component called the **Tailor**, and interacts with other subsystems exclusively through remote interface calls, enabling a truly decoupled, distributed architecture.

### 🛠 Technical Stack & Concepts

- **Build Automation (Maven)**: Multi-module Maven project structure ensuring platform-independent compilation and deployment. Leverages dependency management for seamless integration across distributed systems and CI/CD pipelines.
- **Distributed Systems & Java RMI** — Core concept of the project: subsystems communicate over the network using remote method invocations on exported stubs, with a custom registry ("Tailor") acting as the service-discovery backbone that stitches all components together.
- **JavaFX GUI** — Every subsystem (except the Tailor) provides a live graphical interface showing real-time simulation data such as water levels, discharge rates, and rainfall.

---

## 🎯 The Task

Design and implement a **distributed river-network simulator** where each component — river sections, retention basins, an environment (weather) module, and a control center — operates as an independent process. All inter-process communication must be realized through **Java RMI** remote interfaces (extending `java.rmi.Remote`). A special **Tailor** (Krawiec) subsystem serves as a custom stub registry: every component registers its remote stub with the Tailor on startup and retrieves the stubs of other components it needs to communicate with, effectively "sewing" the distributed system together.

### 🧩 Implementation Logic

| Component | Role |
|---|---|
| **Tailor** | Creates the RMI registry programmatically, registers its own stub, and acts as a name-based lookup service for all other subsystems. |
| **RiverSection** | Receives rainfall data from the Environment and discharge data from upstream basins. Forwards the combined water flow to the next downstream RetentionBasin. |
| **RetentionBasin** | Accumulates inflow from one or more river sections. Manages current volume (`vNow`) against maximum capacity (`vMax`). Regulates outflow (discharge) to the next river section. |
| **Environment** | Simulates weather by periodically generating random rainfall values and pushing them to all registered river sections. |
| **ControlCenter** | Monitors all retention basins in real time (filling %, discharge rate). Allows the operator to manually adjust basin discharge via the GUI. |

### ⚖️ Simulation Criteria

- Each retention basin has a **maximum capacity** (`vMax = 1000 m³`) and an **initial volume** (`vNow = 400 m³`).
- If a basin reaches full capacity, outflow is forced to equal total inflow (overflow prevention).
- If current volume drops to zero, outflow ceases.
- The **Environment** generates rainfall every **5 seconds** in the range of `0–19 m³/s`.
- River sections propagate water flow on a configurable interval (parameter `h` in ms), simulating river segment length/delay.
- All subsystem discovery is **dynamic** — components look up peers by name through the Tailor at runtime.

---

## 🚀 How to Run

### ⚙️ Prerequisites

| Tool | Version |
|---|---|
| **JDK** | 25+ (required by `floodlib` bytecode) |
| **Apache Maven** | 3.8+ |
| **JavaFX SDK** | Bundled via Maven dependencies |

> **Note:** The `floodlib-1.0-SNAPSHOT.jar` must be available at the path specified in `pom.xml` (system-scoped dependency). Adjust the `<systemPath>` if necessary.
a
## 📂 Project Structure

```
src/
└── main/
    └── java/
        └── org/
            └── padadak/
                ├── Tailor.java              # RMI service-discovery registry
                ├── Launcher.java            # Entry point (launches MultiLauncher)
                ├── MultiLauncher.java       # Spawns all subsystem GUI windows
                ├── BasinApp.java            # Standalone RetentionBasin app
                ├── RiverApp.java            # Standalone RiverSection app
                ├── ControlCenterApp.java    # Standalone ControlCenter app
                ├── EnvironmentApp.java      # Standalone Environment app
                └── apps/
                    ├── ControlCenter.java   # ControlCenter (multi-launch version)
                    ├── Enviroment.java       # Environment (multi-launch version)
                    ├── RetentionBasin.java  # RetentionBasin (multi-launch version)
                    └── RiverSection.java    # RiverSection (multi-launch version)
```

---

*Return to [Main Repository](../)*
