TRUNCATE TABLE wallet_entity CASCADE;
TRUNCATE TABLE user_entity CASCADE;
TRUNCATE TABLE transaction_entity CASCADE;

INSERT INTO user_entity (id, email, first_name, last_name, password) VALUES
('fa1e2b3c-4d5e-6f7a-8b9c-0d1e2f3b4a6d', 'msonter@gmail.com', 'mson', 'ter', 'password'),
('c5b3a2d4-8e7f-4a3d-b2c3-f1e2d3b9c4f6', 'johndoe@gmail.com', 'john', 'doe', 'password'),
('c5b3a2d4-7b7f-4a3d-b2c3-f1e2d3b9c4f7', 'asa@gmail.com', 'asa', 'doe', 'password');


INSERT INTO wallet_entity (id, balance, date_created, pin, user_id) VALUES
('f5d2c1d2-1e2b-4c2b-9f2d-81c3b3d9e401', 400.00, '2024-11-15T08:30:00', '2222', 'fa1e2b3c-4d5e-6f7a-8b9c-0d1e2f3b4a6d'),
('9b9e3b9c-4f3c-4c4b-8e3d-92d4d3d9a502', 600.00, '2024-11-14T14:45:00', '1111', 'c5b3a2d4-8e7f-4a3d-b2c3-f1e2d3b9c4f6'),
('6d7e8a7a-6f5a-4c3b-9f3e-a3f5e5b6c703', 500.00, '2024-11-13T10:00:00', '5555', 'c5b3a2d4-7b7f-4a3d-b2c3-f1e2d3b9c4f7');

