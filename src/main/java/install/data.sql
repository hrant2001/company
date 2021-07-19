CREATE SCHEMA IF NOT EXISTS company;

CREATE TABLE IF NOT EXISTS positions (
    position_id TINYINT PRIMARY KEY AUTO_INCREMENT,
    position_name VARCHAR(50) NOT NULL,
    position_short_name VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS department (
    department_id TINYINT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    fname VARCHAR(50) NOT NULL,
    lname VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL,
    position_id TINYINT NOT NULL,
    department_id TINYINT NOT NULL,
    FOREIGN KEY (position_id) REFERENCES positions(position_id),
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);

CREATE TABLE IF NOT EXISTS attendance_record (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    entrance_time DATETIME,
    exit_time DATETIME,
    employee_id INT NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

INSERT INTO positions(position_name, position_short_name)
VALUES ('Intern Java Developer', 'java_int');

INSERT INTO department(department_name)
VALUES ('Development');
