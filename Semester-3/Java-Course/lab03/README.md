# Hair Salon Management System

[![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Java 21](https://img.shields.io/badge/Java_21-ED8B00?style=flat-square&logo=java&logoColor=white)](https://www.oracle.com/java/)

## 📖 Project Description

A multi-user hair salon management system that enables multiple concurrent application instances to perform CRUD operations on shared data persisted in a SQLite database. The system implements a clean separation between business logic and user interface, allowing different user roles (Owner, Cashier, Employee, Client) to interact with a unified data model through dedicated console applications.

---

## 🛠 Technical Stack & Concepts

* **Build Automation (Maven)** – Cross-platform portability and dependency management using Maven for reproducible builds across different development environments
* **Database Persistence & Multi-User Access** – SQLite file-based database with custom exception handling to manage concurrent access from multiple application instances
* **Modular Project Structure** – Clear separation of concerns with dedicated packages for data access (DAO pattern), business logic, user interface, models, and custom exceptions

---

## 🎯 The Task

Develop a small-scale enterprise system that simulates hair salon operations with support for multiple user roles accessing shared data simultaneously. The system must handle CRUD operations on reservations, service pricing, facility management, and user profiles while managing the challenges of concurrent multi-application access to file-based persistence mechanisms.

### 🧩 Implementation Logic

The system enforces business rules for reservation management:

* **Date & Time Validation** – Reservations are restricted to business hours (09:00 - 18:00) with 15-minute interval constraints
* **Temporal Constraints** – Prevents past-dated reservations and enforces minimum 30-minute intervals between consecutive bookings for the same employee
* **Conflict Resolution** – Detects and prevents duplicate reservations at identical time slots and facility stations
* **Role-Based Access Control** – Each application type enforces permissions: Owners manage pricing and view all data, Cashiers settle transactions, Employees track assignments, Clients manage personal reservations only

---

## 📥 Input Data Format

### Reservation Parameters

All user inputs follow strict validation rules:

```
Date Format:     yyyy-MM-dd (e.g., 2026-02-16)
Time Format:     HH:mm (24-hour, e.g., 14:30)
Time Intervals:  Must end in 00, 15, 30, or 45 minutes
Business Hours:  09:00 - 18:00
Facility Names:  Case-sensitive string identifiers
Employee Names:  Case-sensitive string identifiers
Service Names:   Exact match from price list
```

---

## 🚀 How to Run

### ⚙️ Prerequisites

* Java 17 or higher
* Maven 3.6+
* SQLite JDBC driver (automatically managed via Maven)

### 🔨 Build

```bash
mvn clean package
```

This command compiles source code, runs tests (if available), and packages the project into an executable JAR file.

### ▶️ Run

The system consists of four separate applications. Start each in its own terminal:

**Owner Application:**
```bash
java -cp "target/hair-salon-system.jar;target/dependency/*" org.padadak.apps.OwnerApp
```

**Cashier Application (run multiple instances):**
```bash
java -cp "target/hair-salon-system.jar;target/dependency/*" org.padadak.apps.CashierApp
```

**Employee Application (run multiple instances):**
```bash
java -cp "target/hair-salon-system.jar;target/dependency/*" org.padadak.apps.EmployeeApp
```

**Client Application (run multiple instances):**
```bash
java -cp "target/hair-salon-system.jar;target/dependency/*" org.padadak.apps.ClientApp
```

### 📋 Sample Output

```
List of Clients:
 - John Smith
 - Mila Kunis
Welcome, please enter your name or leave: 
John Smith
Name and Surname: John Smith
Role: Client
-------------------------
To do(make reservation, my reservations, leave): make reservation
Where you want make reservation?
List of facilities:
 - Downtown Salon & Spa
 - Grand Royal Barbershop

Enter facility name: Downtown Salon & Spa
Who would you like to book an appointment with?
Employee list:
Alex Rivera

Write name: Alex Rivera
Write date (yyyy-MM-dd): 2026-02-20
Write time (HH:mm): 14:30
List of prices:
 - Classic Haircut cost: 100
 - Beard Trim cost: 70
 - Luxury Grooming cost: 300

Enter service name: Classic Haircut
Reservation was successfully made.

Name and Surname: John Smith
Role: Client
-------------------------
To do(make reservation, my reservations, leave): my reservations
List of reservations:
- ID[4] - (Downtown Salon & Spa) Date: 2025-10-20-17:00 ServiceName: Classic Haircut. Is done: true
- ID[6] - (Downtown Salon & Spa) Date: 2027-10-20-17:00 ServiceName: Classic Haircut. Is done: false
- ID[8] - (Downtown Salon & Spa) Date: 2026-02-20-14:30 ServiceName: Classic Haircut. Is done: false

To do(delete reservation, back): 
back
Name and Surname: John Smith
Role: Client
-------------------------
To do(make reservation, my reservations, leave): leave

Process finished with exit code 0
```

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/padadak/
│   │       ├── Main.java                           # Application entry point
│   │       ├── apps/
│   │       │   ├── OwnerApp.java                  # Owner application interface
│   │       │   ├── CashierApp.java                # Cashier application interface
│   │       │   ├── EmployeeApp.java               # Employee application interface
│   │       │   └── ClientApp.java                 # Client application interface
│   │       ├── data/
│   │       │   └── Database.java                  # SQLite persistence layer & DAO
│   │       ├── logic/
│   │       │   ├── OwnerLog.java                  # Owner business logic
│   │       │   ├── CashierLog.java                # Cashier business logic
│   │       │   ├── EmployeeLog.java               # Employee business logic
│   │       │   └── ClientLog.java                 # Client business logic
│   │       ├── model/
│   │       │   ├── User.java                      # User entity model
│   │       │   ├── Facility.java                  # Facility/Salon entity model
│   │       │   ├── Reservation.java               # Reservation entity model
│   │       │   └── Price.java                     # Service pricing entity model
│   │       └── exceptions/
│   │           ├── InvalidReservationDateFormatException.java
│   │           ├── InvalidReservationTimeFormatException.java
│   │           ├── InvalidReservationTimeStepException.java
│   │           ├── PastReservationTimeException.java
│   │           ├── ReservationAlreadyExistsException.java
│   │           ├── ReservationTimeRangeException.java
│   │           └── TooCloseReservationException.java
│   └── resources/                                  # Configuration files
└── test/                                           # Unit test directory
    └── java/

data/
└── database.db                                     # SQLite database file (auto-created)
```

---
*Return to [Main Repository](../)*

