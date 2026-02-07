# Lab 03: Hair Salon Management System

## 📖 Project Description
This project implements a small management system for a hair salon network (simulated for a single salon).
The system allows multiple types of users to interact with shared data and perform CRUD operations
(Create, Read, Update, Delete) through separate console-based applications.

Each application represents a different role in the salon and operates on the same persisted data
stored in files or a file-based database.

The project is a simplified approximation of real-world salon processes and focuses on clean architecture,
separation of concerns, and basic multi-access data handling.

---

## 🛠 Core Technologies & Concepts
* Console-based Java applications
* CRUD operations
* SQLite file-based database
* SQL-based data persistence
* Separation of business logic and user interface
* Custom domain-specific exceptions

---

## 🎯 The Task
The goal of the system is to support the basic business processes of a hair salon by providing
separate applications for different user roles.

The system must:
* Support CRUD operations on stored data
* Persist data between application runs
* Allow multiple application instances to work on shared data
* Handle file access conflicts using exception handling
* Simulate the passage of time

---

## 👥 User Roles & Applications

### 👑 Owner (OwnerApp)
* Manages the service price list
* Views all reservations
* Views salon income
* Manages system time

### 💰 Cashier (CashierApp)
* Views reservations
* Settles and closes completed reservations
* Operates within a single salon

### ✂️ Employee (EmployeeApp)
* Views reservations assigned to them
* Marks services as completed
* Operates within a single salon

### 👤 Client (ClientApp)
* Views their reservations
* Creates, updates, and deletes their own reservations

Each role is implemented as a separate application, but all applications share the same persisted data.

---

## 🧩 Data Model

### 👥 Person
* id – unique person identifier
* facilityId – salon identifier (Facility)
* name – person name
* role – Owner / Cashier / Employee / Client

### 💲 Price List
* id – unique service identifier
* serviceName – name of the service
* priceValue – service price

### 📅 Reservation
* id – unique reservation identifier
* facilityId – salon identifier (Facility)
* date – reservation date
* time – reservation time
* serviceName – name of the reserved service
* employeeId – assigned employee identifier
* clientId – client identifier
* isCompleted - has it been done (true or false)

### 🏢 Salon (Facility)
* id – unique salon identifier
* name – salon name
* stationsCount – number of workstations
* ownerId – owner identifier
* balance – current salon balance

---

## ⏳ Time Simulation
The system includes a simple time simulation mechanism.
The system time is controlled by the Owner. They can set it in the application.

---

## 🚀 How to Run

### ⚙️ Prerequisites
* JDK 17 or higher
* IntelliJ IDEA or another Java-compatible IDE

---

### 📌 Package Overview

* **apps** – Entry-point applications for each user role (Owner, Cashier, Employee, Client)
* **data** – Database access and persistence logic
* **exceptions** – Custom domain-specific exceptions
* **logic** – Business logic for each role
* **model** – Core data models used across the system
* **data/database.db** – File-based database shared by all applications


