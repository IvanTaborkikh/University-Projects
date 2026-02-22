# Distributed River Network Simulator

![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white&labelColor=C71A36)
![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=java&logoColor=white&labelColor=ED8B00)
![TCP/IP](https://img.shields.io/badge/Protocol-TCP%2FIP-005C84?style=for-the-badge&logo=internet-explorer&logoColor=white&labelColor=005C84)
![Sockets](https://img.shields.io/badge/Networking-Sockets-FF5722?style=for-the-badge&logo=socket.io&logoColor=white&labelColor=FF5722)
![Distributed](https://img.shields.io/badge/Architecture-Distributed-28a745?style=for-the-badge&labelColor=28a745)
![Concurrency](https://img.shields.io/badge/Concurrency_&_Multithreading-007396?style=for-the-badge&logo=java&logoColor=white)
## 📖 Project Description

This is a distributed system simulator that models a river network with retention basins (reservoirs), river sections, atmospheric precipitation, and centralized control. The system employs multi-threaded TCP/IP socket communication to enable separate microservice-like applications to coordinate water flow simulation across a complex hydrological network. Each component communicates asynchronously in real-time, simulating realistic water dynamics including inflows from rainfall, basin discharge management, and flow propagation through river sections.

### 🛠 Technical Stack & Concepts

- **Build Automation (Maven)**: Multi-module Maven project structure ensuring platform-independent compilation and deployment. Leverages dependency management for seamless integration across distributed systems and CI/CD pipelines.
- **Core Technical Concept**: **Distributed Networking with TCP/IP Sockets** – Multi-threaded server-client architecture enabling inter-process communication (IPC) over ServerSocket and Socket APIs for coordinated state management in a geographically dispersed system.
- **Modular Project Structure**: Strict Separation of Concerns with four autonomous subsystems (RetensionBasin, RiverSection, Environment, ControlCenter), each implementing domain-specific interfaces and managing independent responsibilities.

---

## 🎯 The Task

Design and implement a real-time distributed water network simulator where independent Java applications represent hydrological components. The system must accurately model:

1. **Retention Basins** – Reservoirs with configurable maximum capacity, regulated discharge (spillway gates), and inflow accumulation from multiple river sections
2. **River Sections** – Flow segments connecting basins with discrete time-step propagation delays and atmospheric precipitation injection
3. **Environment** – Centralized precipitation generator distributing rainfall data to river sections
4. **Control Center** – Operator console for basin monitoring and spillway gate control

The entire system operates with discretized time intervals where water propagation follows vertical-wave physics (instantaneous level rise, delayed flow transport).

### 🧩 Implementation Logic

**Water Flow Mechanics:**
- Inflow to a basin = water from upstream discharge + atmospheric rainfall from the basin's catchment area
- Basin filling: `V_now = V_now - discharge + inflow` (per time step)
- Overflow protection: If `V_now > V_max`, automatically adjust discharge to match inflow (spillway activation)
- River section transport: Water discharged from basin *i* arrives at basin *i+1* after delay *t*, where *t* is proportional to river segment length

**Component Responsibilities:**
- **RetensionBasin**: Executes water balance equations, exposes IRetensionBasin interface for remote queries/commands
- **RiverSection**: Buffers and delays inflow, combines discharge from upstream basin with rainfall, exposes IRiverSection interface
- **Environment**: Generates rainfall values, distributes via IRiverSection.setRainfall() to all registered segments
- **ControlCenter**: Queries basin states (IRetensionBasin.getFillingPercentage(), getWaterDischarge()), commands gate adjustments via setWaterDischarge()

**Communication Protocol:**
All inter-process requests use text-encoded TCP/IP messages following a context-free grammar:
- `gwd` → retrieve current discharge from basin
- `gfp` → retrieve filling percentage of basin
- `swd:VALUE` → set basin discharge to VALUE (m³/s)
- `swi:VALUE,PORT` → set inflow from river section on PORT to VALUE (m³/s)
- `srd:VALUE` → set real discharge from upstream basin to VALUE (m³/s)
- `srf:VALUE` → set rainfall in river segment to VALUE (m³/s)
- `arb:PORT,HOST` → register river section at PORT/HOST with downstream basin
- `ars:PORT,HOST` → register basin at PORT/HOST with upstream river section

### ⚖️ Optimization/Process Criteria

- **Concurrency Safety**: All basin state mutations protected by synchronized blocks to prevent race conditions across concurrent client threads
- **Scalability**: Multi-threaded ServerSocket accept loops enable unlimited simultaneous basin-river connections
- **Fault Tolerance**: Try-catch blocks around socket operations gracefully handle disconnections; daemon threads for background simulation
- **Latency Simulation**: Configurable millisecond delays in RiverSection mirror real hydraulic transport lag
- **Discretized Simulation**: 1000ms time steps in basin simulation threads ensure consistent water balance calculations

---

## 📥 Input Data Format
### Protocol: ControlCenter Commands to Basins

**ControlCenter** connects to **RetensionBasin** and sends commands. Valid requests and responses:

```
Request: gwd                  Response: <DISCHARGE_VALUE>  (Get Water Discharge in m³/s)
Request: gfp                  Response: <PERCENTAGE>        (Get Percentage Filling)
Request: swd:<VALUE>          Response: OK                  (Set Water Discharge)
Request: ars:<PORT>,<HOST>    Response: OK                  (Assign River Section)
```

**Actual ControlCenter Session Example:**
```
Please enter a port:       
Server is working             [ControlCenter listening on port 3000]     
4004 is connected
4004                          [User specifies basin port]
gwd                           [Request: Get Water Discharge]
2500                          [Response: Current discharge]
ars:3004,localhost            [Request: Register River Section on port 3004, host localhost]
OK                            [Response: Acknowledged]
swd:100                       [Request: Set Water Discharge to 100 m³/s]
OK                            [Response: Accepted]
gwd                           [Request: Get Water Discharge]
2555                          [Response: New current discharge]
gpf                           [Invalid: should be 'gfp' not 'gpf']
Error                         [Response: Command not recognized]
```

### Protocol: Environment Commands RiverSections

**Environment** connects to **RiverSection** and sends commands. Valid requests and responses:

```
Request: ars:<PORT>,<HOST>    Response: OK                  (RiverSection registration)
Request: arb:<PORT>,<HOST>    Response: OK                  (Basin info forwarding from RiverSection)
```

**Actual Environment Session Example:**
```
Please enter a port: 
Server is working             [Environment listening on port 3001]
3004 is connected             [Response: registration confirmed]
3004
arb:4004,localhost            [RiverSection forwards basin info: RetensionBasin on port 4004, host localhost]
OK                            [Response: basin info acknowledged]
```

### Complete Message Grammar

```
<digit_nonzero> = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
<digit>         = "0" | <digit_nonzero>
<port>          = <digit_nonzero> { <digit> }
<host_octet>    = <digit_nonzero> { <digit> }  (0–255)
<host>          = <host_octet> "." <host_octet> "." <host_octet> "." <host_octet>

<get_method>    = "gwd" | "gfp"
<set_method>    = "swd:" <port> | "swi:" <port> "," <port> | "srd:" <port> | "srf:" <port>
<assign_method> = "arb:" <port> "," <host> | "ars:" <port> "," <host>

<request>       = <get_method> | <set_method> | <assign_method>
<response>      = <port> | "OK" | "Error"
```

## 🚀 How to Run

### ⚙️ Prerequisites

* Java 21 or higher
* Maven 3.8+  
* Network Configuration – All components default to `localhost`; modify host strings in connect() methods for multi-machine deployment

### 🔨 Build

```bash
mvn clean package
```
This command compiles source code, runs tests (if available), and packages the project into an executable JAR file.

### ▶️ Run
> [!IMPORTANT]
> **Startup Order is Critical:** You **must** launch the `Environment` and `ControlCenter` before starting any `RetensionBasin` or `RiverSectіon`. If components are started in a different order, connection errors will occur because the hydrological nodes will fail to register with the central controllers.

**Terminal 1 – Environment:**
```bash
java -cp "target/river-network-simulator.jar;target/dependency/*" org.padadak.Environment
```

**Terminal 2 – ControlCenter:**
```bash
java -cp "target/river-network-simulator.jar;target/dependency/*" org.padadak.ControlCenter
```

**Terminal 3 – RetensionBasin:**
```bash
java -cp "target/river-network-simulator.jar;target/dependency/*" org.padadak.RetensionBasin 5001
```

**Terminal 4 – RiverSection:**
```bash
java -cp "target/river-network-simulator.jar;target/dependency/*" org.padadak.RiverSection 6001 1000
```

Wait 2–3 seconds between launching each component to allow TCP handshakes and registration.


---

## 📂 Project Structure

```
lab06/
├── pom.xml                                    # Maven build configuration
├── README.md                                  # This file
└── src/
    ├── main/
    │   ├── java/
    │   │   └── org/padadak/
    │   │       ├── ControlCenter.java         # Operator console (port 3000)
    │   │       ├── Environment.java           # Rainfall generator (port 3001)
    │   │       ├── RetensionBasin.java        # Reservoir simulator
    │   │       ├── RiverSection.java          # Flow segment simulator
    │   │       └── interfaces/
    │   │           ├── IControlerCenter.java  # ControlCenter contract
    │   │           ├── IEnvironment.java      # Environment contract
    │   │           ├── IRetensionBasin.java   # RetensionBasin contract
    │   │           └── IRiverSection.java     # RiverSection contract
    │   └── resources/                         # Configuration files (if any)
    └── test/
        └── java/                              # Unit tests (if any)
```

---
*Return to [Main Repository](../)*
