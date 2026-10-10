📚 SQL-Backend Java Library Management System

A robust, console-driven Library Management application built using pure *Java Core* and *MySQL Database* relational storage connected via *JDBC*.

This project goes beyond simple CRUD configurations by introducing tracking algorithms for matching inventories and synchronized data state engines.

---

## ✨ Key Technical Features

### 1. Unified Atomic Transaction Control
Instead of relying on fragile, multi-query steps prone to race conditions, the system executes book borrowing through a combined conditional INSERT INTO ... SELECT query framework. A borrowing ticket is *never generated* if the book copy count is out of stock.

### 2. Auto-Decrementing Inventory Tracker
Tracks multi-copy collections of duplicate book titles. When an issue transaction succeeds, the physical database count drops dynamically (available_copies - 1). When a copy is returned, it safely scales the inventory ledger back up (available_copies + 1) without exceeding maximum initial capacity sizes.

### 3. Data Access Object (DAO) Architectural Pattern
Engineered following strict structural isolation design rules. Database connection management and raw query routines are completely isolated into independent middleware classes, keeping presentation terminal shells entirely separate from transactional backends.

---

## 🛠️ Technology Stack & Dependencies

*   *Language Environment:* Java Core (JDK 17 or higher)
*   *Relational Engine:* MySQL Server 8.x+
*   *Driver Interface:* JDBC (MySQL Connector/J com.mysql.cj.jdbc.Driver)

---

## 💾 Relational Database Schema Design

The backend database contains a synchronized dual-table layout managed inside the library_db sandbox workspace:

### 📋 books Table
Tracks core publication details along with inventory tracking indicators.
*   id (VARCHAR, Primary Key) - Unique alphanumeric identifier for each book title.
*   booktitle (VARCHAR) - Name of the book.
*   bookauthor (VARCHAR) - Author's name.
*   totalCopies - Total stock supply owned by the library.
*   availableCopies (INT) - Real-time stock copies available on shelves.

### 📋 transactions Table
Logs student ledger metadata linked to inventory indices.
*   trans_id (INT, Primary Key Auto-Increment)
*   book_id (VARCHAR, Foreign Key referencing books.id)
*   student_id (VARCHAR) - Unique Identifier of the borrowing student.
*   student_name (VARCHAR) - Full name of the borrowing student.
*   issue_date (TIMESTAMP) - Automatically captured check-out time marker.
*   status (VARCHAR) - Tracking flag initialized as ISSUED and modified to RETURNED.

---

## 💻 Code Structure Breakdown

text
src/

└── library/

├── DBConnection.java   -------   # Manages Connection pool routing directly to local MySQL instances.

├── Book.java   ---------        # Data Model object holding real-time metadata states.

├── LibraryDAO.java ---------    # The Business Engine executing parameterized statements & transaction controls.

└── Main.java ---------  # Interactive application menu shell processing inputs.


---

## 🚀 Setup & Execution Guide

### 1. Database Configuration
Execute the structural setup configurations in your local MySQL instance terminal:
sql

CREATE DATABASE library_db;

USE library_db;

CREATE TABLE books (
id VARCHAR(50) PRIMARY KEY,

booktitle VARCHAR(255) NOT NULL,

bookauthor VARCHAR(255) NOT NULL,

totalCopies INT NOT NULL DEFAULT 1,

availableCopies INT NOT NULL DEFAULT 1
);

ANOTHER TABLE

CREATE TABLE transactions (
trans_id INT AUTO_INCREMENT PRIMARY KEY,

book_id VARCHAR(50),

student_id VARCHAR(50) NOT NULL,

student_name VARCHAR(255) NOT NULL,

issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

status VARCHAR(20) DEFAULT 'ISSUED',

FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE SET NULL
);


### 2. Connect Your Credentials
Open DBConnection.java inside your workspace project and update your driver authorization values:


private static final String USER = "your_mysql_username";

private static final String PASSWORD = "your_mysql_password";


### 3. Execution
Add your mysql-connector-j-x.x.x.jar library file to your IDE's library build-path, open Main.java, and run the file to spin up the smart interactive dashboard panel.