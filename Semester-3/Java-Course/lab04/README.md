
A Java application for visualizing maritime shipping data from the Polish Central Statistical Office (GUS) API. This project demonstrates how to build a modular, multi-tier JavaFX application that fetches, parses, and visualizes data from public web APIs.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Architecture](#project-architecture)
- [Technology Stack](#technology-stack)
- [Requirements](#requirements)
- [Installation & Setup](#installation--setup)
- [Building the Project](#building-the-project)
- [Running the Application](#running-the-application)
- [API Information](#api-information)
- [Project Structure](#project-structure)
- [Code Translation Notes](#code-translation-notes)

## 🎯 Overview

This application provides a user-friendly graphical interface for browsing and analyzing maritime shipping data through the GUS (Główny Urząd Statystyczny - Central Statistical Office) Public API. Users can visualize:

- The number of ships in Polish ports (Gdańsk, Gdynia, Szczecin, Świnoujście)
- Ship statistics filtered by year (2019-2023)
- Ship types distribution (Passenger, Cargo, Tanker, Other)
- Historical trends displayed as interactive line charts

The application is built using **JavaFX** for the GUI and follows the **Java Platform Module System (JPMS)** for modularity and proper encapsulation.

## ✨ Features

- **Interactive GUI**: Built with JavaFX for a modern, responsive user interface
- **Real-time Data Fetching**: Retrieves data directly from the GUS API
- **Data Visualization**: Displays statistics as interactive line charts
- **Multi-year Analysis**: Compare ship statistics across different years
- **Port Filtering**: View data for specific Polish ports
- **Ship Type Classification**: Analyze data by ship types (Passenger, Cargo, Tanker, Other)
- **Modular Architecture**: Separated client and GUI modules for maintainability
- **Error Handling**: Robust handling of network requests and data parsing

## 🏗️ Project Architecture

The project follows a modular design pattern with two main components:

```
lab04/
├── client/               # Data fetching and processing module (lab04_client.jar)
│   └── src/main/java/org/padadak/client/
│       ├── Test.java     # Basic test client
│       ├── classes/
│       │   ├── Request.java      # HTTP requests to GUS API
│       │   └── SSLUtils.java     # SSL verification utilities
│       └── objects/
│           ├── ShipsInPorts.java       # Data model for port statistics
│           └── ShipTypeInPort.java     # Data model for ship type statistics
│
├── gui/                  # GUI module (lab04_gui.jar)
│   └── src/main/java/org/padadak/gui/
│       ├── Main.java     # JavaFX application entry point
│       └── graf/
│           └── Graf.java # Chart generation and visualization
│
└── pom.xml              # Parent Maven POM configuration
```

### Module Dependencies

- **gui** → **client**: GUI module depends on client module for data operations
- **client** → **java.base**, **java.net.http**: Uses standard Java libraries
- Both modules follow JPMS specifications in `module-info.java`

## 🛠️ Technology Stack

- **Java**: Version 21 (Java 21 SDK required)
- **Build Tool**: Apache Maven 3.9+
- **GUI Framework**: JavaFX 21.0.2
- **JSON Processing**: Jackson Databind 2.13.0
- **HTTP Client**: Java built-in HttpURLConnection
- **Module System**: Java Platform Module System (JPMS)

## 📋 Requirements

Before running this project, ensure you have:

1. **Java Development Kit (JDK)** - Version 21 or higher
    - Download from: https://www.oracle.com/java/technologies/downloads/
    - Or use OpenJDK 21: https://openjdk.org/projects/jdk/21/

2. **Apache Maven** - Version 3.9 or higher
    - Download from: https://maven.apache.org/download.cgi

3. **JavaFX SDK** - Version 21.0.2 (if using standard JDK, not Liberica JDK Full)
    - Download from: https://gluonhq.com/products/javafx/

4. **Internet Connection**: Required for API calls to GUS servers

5. ⚠️ **API Rate Limiting**: The GUS API has rate limits. Please be respectful when testing and avoid excessive requests.

## 📦 Installation & Setup

### 1. Clone or Extract the Repository

```bash
cd /path/to/lab04
```

### 2. Verify Java and Maven Installation

```bash
java -version
mvn -version
```

### 3. Configure JavaFX (if needed)

If using standard JDK (not Liberica JDK Full), you may need to:

1. Download JavaFX SDK 21.0.2
2. Extract it to a known location (e.g., `C:\javafx-sdk-21.0.2` on Windows or `/opt/javafx-sdk-21.0.2` on Linux)
3. The Maven POM is already configured with JavaFX dependencies

## 🔨 Building the Project

### Build All Modules

```bash
mvn clean install
```

This will:
1. Compile both client and GUI modules
2. Run tests (if any)
3. Package both JARs in their respective `target/` directories
4. Build artifacts:
    - `client/target/client-1.0-SNAPSHOT.jar`
    - `gui/target/gui-1.0-SNAPSHOT.jar`

### Build Only Client Module

```bash
mvn -pl client clean install
```

### Build Only GUI Module

```bash
mvn -pl gui clean install
```

### Build with Specific JavaFX Path (if needed)

```bash
mvn clean install -Djavafx.version=21.0.2
```

## 🚀 Running the Application

### Method 1: Run from IDE (Recommended for Development)

1. Open the project in IntelliJ IDEA or another IDE
2. Navigate to `gui/src/main/java/org/padadak/gui/Main.java`
3. Click the "Run" button or press `Shift+F10` (IntelliJ)

### Method 2: Run from Command Line (After Building)

#### On Windows (PowerShell):

```powershell
# Navigate to project directory
cd E:\University-Projects\Semester-3\Java-Course\lab04

# Build the project
mvn clean install

# Run the GUI application
java --module-path gui\target\gui-1.0-SNAPSHOT.jar;client\target\client-1.0-SNAPSHOT.jar --add-modules gui,client -m gui/org.padadak.gui.Main
```

#### On Linux/macOS (Bash):

```bash
# Navigate to project directory
cd ~/path/to/lab04

# Build the project
mvn clean install

# Run the GUI application
java --module-path gui/target/gui-1.0-SNAPSHOT.jar:client/target/client-1.0-SNAPSHOT.jar --add-modules gui,client -m gui/org.padadak.gui.Main
```

### Method 3: Run Client Test (Data Fetching Only)

```bash
java --module-path client/target/client-1.0-SNAPSHOT.jar --add-modules client -m client/org.padadak.client.Test
```

### Method 4: Direct Execution with JavaFX (if using separate JavaFX SDK)

#### Windows:

```powershell
java --module-path C:\javafx-sdk-21.0.2\lib;gui\target\gui-1.0-SNAPSHOT.jar;client\target\client-1.0-SNAPSHOT.jar `
     --add-modules javafx.controls,javafx.fxml,gui,client `
     -m gui/org.padadak.gui.Main
```

#### Linux/macOS:

```bash
java --module-path /opt/javafx-sdk-21.0.2/lib:gui/target/gui-1.0-SNAPSHOT.jar:client/target/client-1.0-SNAPSHOT.jar \
     --add-modules javafx.controls,javafx.fxml,gui,client \
     -m gui/org.padadak.gui.Main
```

## 🌐 API Information

### Data Source

**GUS (Central Statistical Office) Public API**
- **Base URL**: https://api-transtat.stat.gov.pl/
- **API Documentation**: https://api-transtat.stat.gov.pl/apidocs/index.html
- **Service Portal**: https://api.stat.gov.pl/Home/TranStatApi

### Used Endpoints

1. **Ships in Ports** (C001MInd111p)
    - URL: `https://api-transtat.stat.gov.pl/api/v1/C001MInd111p/?format=json`
    - Purpose: Retrieves total number of ships in all Polish ports by date
    - Data includes: Gdańsk, Gdynia, Szczecin, Świnoujście

2. **Ships by Type** (C003MInd113p)
    - URL: `https://api-transtat.stat.gov.pl/api/v1/C003MInd113p/SingleParamPl/{shipType}`
    - Purpose: Retrieves ship counts by type for specific port
    - Ship Types: Passenger (Pasażerski), Cargo (Towarowy), Tanker (Tankowiec), Other (Pozostały)

### Data Format

All responses are in **JSON format** with the following structure:

```json
[
  {
    "id": 1,
    "date": "2023-01-01",
    "gdansk": 25,
    "gdynia": 18,
    "szczecin": 12,
    "swinoujscie": 8
  }
]
```

### API Rate Limiting

⚠️ **Important**: The GUS API enforces rate limiting. Please:
- Avoid sending more than a few requests per second
- Do not repeatedly test large date ranges
- Cache results when possible
- Respect the service's terms of use

## 📁 Project Structure

```
lab04 – копія/
├── README.md                           # This file
├── pom.xml                             # Parent POM (multi-module build)
│
├── client/                             # Client Module (Lab04_client.jar)
│   ├── pom.xml                         # Client module POM
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── module-info.java
│   │   │   │   └── org/padadak/client/
│   │   │   │       ├── Test.java
│   │   │   │       ├── classes/
│   │   │   │       │   ├── Request.java       # HTTP request handler
│   │   │   │       │   └── SSLUtils.java      # SSL utilities
│   │   │   │       └── objects/
│   │   │   │           ├── ShipsInPorts.java  # Port data model
│   │   │   │           └── ShipTypeInPort.java # Ship type data model
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   └── target/                         # Compiled classes and JAR
│
├── gui/                                # GUI Module (Lab04_gui.jar)
│   ├── pom.xml                         # GUI module POM
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── module-info.java
│   │   │   │   └── org/padadak/gui/
│   │   │   │       ├── Main.java       # JavaFX Application entry point
│   │   │   │       └── graf/
│   │   │   │           └── Graf.java   # Chart generation
│   │   │   └── resources/
│   │   └── test/
│   │       └── java/
│   └── target/                         # Compiled classes and JAR
│
└── .gitignore (if using Git)
```

## 📝 Code Translation Notes

This project has been fully translated from Polish to English for international accessibility:

### Translated Elements

| Polish | English | File(s) |
|--------|---------|---------|
| `PortRequest()` | `requestPorts()` | Request.java, Test.java |
| `PortTypeRequest()` | `requestPortTypes()` | Request.java, Test.java, Graf.java |
| `ShowChart()` | `showChart()` | Main.java, Graf.java |
| `ShowTypeChart()` | `showTypeChart()` | Main.java, Graf.java |
| `"Pasażerski"` | `"Passenger"` | Graf.java |
| `"Towarowy"` | `"Cargo"` | Graf.java |
| `"Tankowiec"` | `"Tanker"` | Graf.java |
| `"Pozostały"` | `"Other"` | Graf.java |
| `"YearShip detektor"` | `"Ship Detector by Year"` | Main.java |
| `graf` | `graph` | Main.java |
| `all` | `allData` | Graf.java |

### Variable Naming Convention

All code follows **camelCase** naming convention:
- Methods: `requestPorts()`, `showChart()`, etc.
- Variables: `portList`, `dateList`, `allData`, etc.
- Classes: `ShipsInPorts`, `ShipTypeInPort`, etc.

## 🔗 Related Resources

- **Java 21 Documentation**: https://docs.oracle.com/en/java/javase/21/
- **JavaFX Documentation**: https://openjfx.io/openjfx-docs/
- **Apache Maven Guide**: https://maven.apache.org/guides/
- **Jackson JSON Library**: https://github.com/FasterXML/jackson
- **GUS API Documentation**: https://api-transtat.stat.gov.pl/apidocs/index.html
- **Java Module System**: https://www.baeldung.com/java-9-modularity

## 📖 Examples

### Viewing Ships in Gdańsk (2023)

1. Launch the application
2. Select "Gdansk" from the Port dropdown
3. Select "2023" from the Year dropdown
4. Click "Load Data"
5. A line chart will open showing the daily number of ships

### Viewing Ship Types in Gdynia (2021)

1. Launch the application
2. Select "Gdynia" from the Port dropdown
3. Select "2021" from the Year dropdown
4. Click "Load Ship Type Data"
5. A comparison chart showing all ship types will open

## ⚙️ Troubleshooting

### Issue: "Module not found"
**Solution**: Ensure both `client` and `gui` modules are built before running:
```bash
mvn clean install
```

### Issue: "JavaFX not found"
**Solution**: If using standard JDK, download JavaFX SDK and set module path correctly. Alternatively, use Liberica JDK Full which includes JavaFX.

### Issue: "Connection refused" or "Cannot connect to API"
**Solution**:
- Check your internet connection
- Verify the API service is accessible: https://api-transtat.stat.gov.pl/
- Check if your IP is not rate-limited (wait before retrying)

### Issue: "SSL Verification Failed"
**Solution**: The application automatically disables SSL verification for the GUS API. If issues persist, check your system's certificate store.

### Issue: "No data displayed"
**Solution**:
- Ensure you have selected both a port and a year
- Try a different year from the available range (2019-2023)
- Check console output for API error messages

## 📄 License

This is an academic project created for educational purposes at university.

## 👨‍💼 Author

Created as part of Laboratory 4 (Lab04) for the Java Course, Semester 3.

---

**Last Updated**: February 2025

For questions or issues, please refer to the GUS API documentation or contact your course instructor.
