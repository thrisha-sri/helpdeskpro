
CREATE DATABASE IF NOT EXISTS helpdeskpro;
USE helpdeskpro;

-- 1. Users table
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(150) NOT NULL,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100) DEFAULT NULL,
    job_title VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

-- 2. Technicians table
CREATE TABLE technicians (
    id BIGINT NOT NULL AUTO_INCREMENT,
    available BIT(1) NOT NULL,
    email VARCHAR(150) NOT NULL,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_technicians_email (email)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

-- 3. Tickets table
CREATE TABLE tickets (
    id BIGINT NOT NULL AUTO_INCREMENT,
    createdAt DATETIME(6) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    priority ENUM('CRITICAL', 'HIGH', 'LOW', 'MEDIUM') NOT NULL,
    status ENUM('CLOSED', 'IN_PROGRESS', 'OPEN', 'RESOLVED') NOT NULL,
    title VARCHAR(150) NOT NULL,
    technician_id BIGINT DEFAULT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY idx_ticket_technician (technician_id),
    KEY idx_ticket_user (user_id),
    CONSTRAINT fk_ticket_technician
        FOREIGN KEY (technician_id) REFERENCES technicians(id),
    CONSTRAINT fk_ticket_user
        FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

-- 4. Sample users
INSERT INTO users (name, email, department, job_title)
VALUES
('Arun Kumar', 'arun.sample@example.com', 'IT', 'Java Developer'),
('Priya Sharma', 'priya.sample@example.com', 'Human Resources', 'HR Executive');

-- 5. Sample technicians
INSERT INTO technicians (name, email, specialization, available)
VALUES
('Karthik', 'karthik.sample@example.com', 'Networking', b'1'),
('Divya', 'divya.sample@example.com', 'Hardware Support', b'1');

-- 6. Sample tickets
INSERT INTO tickets
    (title, description, createdAt, priority, status, user_id, technician_id)
VALUES
('Wi-Fi not working',
 'Unable to connect to the office Wi-Fi.',
 CURRENT_TIMESTAMP(6), 'HIGH', 'OPEN', 1, NULL),

('Keyboard issue',
 'Keyboard keys are not responding.',
 CURRENT_TIMESTAMP(6), 'MEDIUM', 'OPEN', 1, NULL);
