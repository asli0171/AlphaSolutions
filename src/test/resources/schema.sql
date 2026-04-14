DROP TABLE IF EXISTS TimeEntry;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS User;

CREATE TABLE User (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(50) NOT NULL
);

CREATE TABLE Project (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         customer VARCHAR(255) NOT NULL,
                         start_date DATE,
                         end_date DATE,
                         status VARCHAR(50)
);

CREATE TABLE Task (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      project_id INT,
                      name VARCHAR(255) NOT NULL,
                      description VARCHAR(1000),
                      FOREIGN KEY (project_id) REFERENCES Project(id) ON DELETE CASCADE
);

CREATE TABLE TimeEntry (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           task_id INT,
                           user_id INT,
                           hours DECIMAL(5,2),
                           date DATE,
                           FOREIGN KEY (task_id) REFERENCES Task(id),
                           FOREIGN KEY (user_id) REFERENCES User(id)
);