DROP TABLE IF EXISTS time_entry;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       competence VARCHAR(255)
);

CREATE TABLE project (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         customer VARCHAR(255) NOT NULL,
                         start_date DATE,
                         end_date DATE,
                         status VARCHAR(50)
);

CREATE TABLE task (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      project_id INT,
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(1000),
                      estimated_hours DECIMAL(5,2) DEFAULT 0,
                      start_date DATE,
                      end_date DATE,
                      FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE time_entry (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            task_id INT,
                            user_id INT,
                            hours DECIMAL(5,2),
                            date DATE,
                            comment VARCHAR(255),
                            FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE,
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);