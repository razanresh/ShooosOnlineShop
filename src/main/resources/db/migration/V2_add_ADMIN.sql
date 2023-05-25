INSERT INTO users (id, archive, email, name, password, role, cart_id)
VALUES (1, false, 'example@gmail.com', 'admin', 'pass', 'ADMIN', null);

ALTER SEQUENCE user_seq RESTART WITH 2;