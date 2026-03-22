# 🌊 Ocean View Resort Management System

## 📖 Overview

The **Ocean View Resort Management System** is a comprehensive Java-based desktop application developed to streamline hotel operations such as room management, reservations, guest handling, and billing.

This system is built using **JavaFX** for the user interface and follows a **Layered Architecture** to ensure maintainability, scalability, and clean separation of concerns. The application also incorporates multiple **design patterns**, demonstrating advanced software engineering principles.

---

## 🎯 Objectives

* To design and implement a real-world hotel management system
* To apply object-oriented programming concepts in Java
* To utilize design patterns effectively
* To develop a user-friendly graphical interface using JavaFX
* To integrate a relational database using MySQL

---

## 🧩 Key Features

### 🏨 Room Management

* Add, update, delete, and view rooms
* Room categorization (Standard, Deluxe, Suite)

### 📅 Reservation Management

* Create and manage reservations
* Edit and delete bookings
* Store guest details including email

### 👤 Guest Management

* Maintain guest records
* Associate guests with reservations

### 💳 Billing System

* Generate bills based on stay duration
* Calculate total cost automatically

### 📊 Dashboard

* Overview of system data
* Easy navigation across modules

### 🔔 Notification System

* Observer pattern used to notify components of reservation updates

---

## 🏗️ System Architecture

The system follows a **Layered Architecture**:

* **Presentation Layer** → JavaFX UI (FXML + Controllers)
* **Service Layer** → Business logic handling
* **DAO Layer** → Database operations
* **Model Layer** → Entity classes

---

## 🧠 Design Patterns Implemented

| Pattern          | Usage                              |
| ---------------- | ---------------------------------- |
| Factory Pattern  | Creating different types of rooms  |
| Observer Pattern | Handling reservation notifications |
| DAO Pattern      | Managing database interactions     |

---

## 🛠️ Technologies Used

* **Programming Language:** Java
* **UI Framework:** JavaFX (FXML + Scene Builder)
* **Database:** MySQL
* **Connectivity:** JDBC
* **IDE:** IntelliJ IDEA

---

## 🗃️ Project Structure

```id="p1a2b3"
src/
 ├── controller/   # UI Controllers
 ├── service/      # Business Logic
 ├── dao/          # Database Access
 ├── model/        # Data Models
 ├── factory/      # Factory Pattern
 ├── observer/     # Observer Pattern
 └── view/         # FXML UI Files
```

---

## ⚙️ Installation & Setup

### 1️⃣ Clone Repository

```bash id="clone1"
git clone https://github.com/your-username/ocean-view-resort.git
```

### 2️⃣ Open Project

* Open IntelliJ IDEA
* Select **Open Project**
* Choose the cloned directory

### 3️⃣ Database Configuration

* Create a MySQL database (e.g., `ocean_view_resort`)
* Import the provided SQL script (if available)
* Update database credentials in your DAO configuration

### 4️⃣ Run Application

* Locate and run the `Main` class

---

## 🧪 Sample Database Tables

* `rooms`
* `reservations`
* `billing`
* `guests`

---

## 🔄 Version Control & Branching Strategy

| Branch  | Purpose                      |
| ------- | ---------------------------- |
| `main`  | Stable production-ready code |
| `alpha` | Development branch           |
| `beta`  | Testing and bug fixing       |

### Example Merge Command

```bash id="merge1"
git checkout alpha
git merge beta
```

---

## ⚠️ Challenges Faced

* Handling JavaFX UI binding with dynamic data
* Managing database connections efficiently
* Resolving merge conflicts in Git
* Maintaining clean architecture across layers

---

## 📈 Future Improvements

* Add user authentication (login system)
* Generate PDF bills
* Integrate online booking
* Improve UI/UX design
* Add reporting and analytics

---

## 👨‍🎓 Author

**Sahan Asantha**
Undergraduate Software Engineering Student

---

## 📜 License

This project is developed for academic purposes and is not intended for commercial use.

---

## 🙏 Acknowledgements

* JavaFX Documentation
* MySQL Documentation
* IntelliJ IDEA

---

## ⭐ Final Note

This project demonstrates the practical application of software engineering concepts including **OOP principles, design patterns, layered architecture, and database integration**, making it a strong academic submission.
