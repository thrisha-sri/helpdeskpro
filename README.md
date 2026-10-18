#  HelpDeskPro Hibernate Ticket Management System

A Java-based helpdesk application built with **Java 21, Hibernate ORM, and MySQL** to manage support tickets, users, and technicians. This project demonstrates object-relational mapping, database operations, business logic, and input validation.

Java 21 | Hibernate ORM | MySQL 8 | Maven

## Table of Contents

- Features
- Technology Stack
- Prerequisites
- Database Setup
- Configuration
- How to Run
- Project Structure
- Learning Outcomes
- Future Improvements

##  Features

### Ticket Management
- Create, view, update, and delete support tickets
- Track ticket status: `OPEN`, `IN_PROGRESS`, `RESOLVED`, and `CLOSED`
- Set ticket priority: `LOW`, `MEDIUM`, `HIGH`, and `CRITICAL`
- Filter tickets by status and priority
- View tickets assigned to a particular technician
- Retrieve tickets using pagination

### User & Technician Management
- Create and view users and technicians
- Assign technicians to support tickets
- Manage technician availability
- Validate input and reject invalid records
- Prevent assignment to unavailable technicians and prevent reassignment through the current assignment method

###  Dashboard & Reporting
- Display total ticket count
- Summarize tickets by status and priority
- View ticket details with associated user and technician information

##  Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| Hibernate ORM 7.1 | Object-relational mapping |
| MySQL 8 | Relational database |
| Maven | Dependency management and build |
| HikariCP | Database connection pooling |
| HQL | Hibernate Query Language |
| Eclipse IDE | Development environment |

##  Prerequisites

- JDK 21 or compatible Java 21 development environment
- MySQL Server 8 or a compatible version
- Maven
- Eclipse IDE or another Java IDE

##  Database Setup

1. Start your MySQL server.
2. Open `src/main/resources/schema.sql`.
3. Review the script and execute it to create the database and required tables.

**Important:** `schema.sql` is intended for a fresh database setup. Back up existing data and review the script before running it against any database that already contains records.

##  Configuration

The application reads database credentials from environment variables.

Configure the following variables in your IDE's run configuration:

| Variable | Value |
|---|---|
| `HELPDESK_DB_USERNAME` | Your MySQL username |
| `HELPDESK_DB_PASSWORD` | Your MySQL password |

Confirm that the JDBC URL in `src/main/resources/hibernate.cfg.xml` points to your local `helpdeskpro` database.

## How to Run

### Using Eclipse

1. Clone or download this repository.
2. Import it into Eclipse as an **Existing Maven Project**.
3. Select JDK 21 for the project.
4. Configure the database environment variables.
5. Ensure the MySQL server is running and the database is configured.
6. Run `com.helpdeskpro.main.HelpDeskProApp` as a Java application.

### Using Maven

Compile the application from the project root:

bash
mvn clean compile

Maven resolves dependencies declared in `pom.xml`. Run the console application through Eclipse after configuring the required environment variables.

##  Project Structure
helpdeskpro/
├── src/
│   ├── main/
│   │   ├── java/com/helpdeskpro/
│   │   │   ├── config/
│   │   │   │   └── HibernateUtil.java
│   │   │   ├── dao/
│   │   │   │   ├── TechnicianDAO.java
│   │   │   │   ├── TicketDAO.java
│   │   │   │   └── UserDAO.java
│   │   │   ├── entity/
│   │   │   │   ├── Technician.java
│   │   │   │   ├── Ticket.java
│   │   │   │   ├── TicketPriority.java
│   │   │   │   ├── TicketStatus.java
│   │   │   │   └── User.java
│   │   │   ├── main/
│   │   │   │   └── HelpDeskProApp.java
│   │   │   └── service/
│   │   │       ├── TechnicianService.java
│   │   │       ├── TicketService.java
│   │   │       └── UserService.java
│   │   └── resources/
│   │       ├── hibernate.cfg.xml
│   │       └── schema.sql
├── .gitignore
├── pom.xml
└── README.md

## Learning Outcomes

This project demonstrates:

- Object-oriented programming with Java
- Hibernate entity mapping and relationships
- CRUD operations and transaction management
- HQL queries, filtering, and pagination
- Service and DAO layer separation
- Input validation and exception handling
- MySQL integration and connection pooling

##  Future Improvements

- Add automated unit and integration tests
- Introduce role-based access control
- Build a Spring Boot REST API
- Develop a web-based user interface
- Add ticket search, sorting, and reporting enhancements


**Built as a Java and Hibernate learning project.**
