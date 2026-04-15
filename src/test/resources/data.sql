INSERT INTO users (username, password, role) VALUES
                                                 ('admin', 'adminpassword', 'ADMIN'),
                                                 ('user1', 'password1', 'USER'),
                                                 ('user2', 'password2', 'USER');

INSERT INTO project (name, customer, start_date, end_date, status) VALUES
                                                                       ('Project 1', 'Customer 1', '2026-04-01', '2026-05-01', 'Ikke begyndt'),
                                                                       ('Project 2', 'Customer 2', '2026-04-10', '2026-06-01', 'Igang');

INSERT INTO task (project_id, name, description) VALUES
                                                     (1, 'Task 1', 'Description for task 1'),
                                                     (1, 'Task 2', 'Description for task 2'),
                                                     (2, 'Task 3', 'Description for task 3');

INSERT INTO time_entry (task_id, user_id, hours, date) VALUES
                                                           (1, 2, 4.5, '2026-04-05'),
                                                           (2, 3, 3.0, '2026-04-10'),
                                                           (3, 2, 2.0, '2026-04-15');