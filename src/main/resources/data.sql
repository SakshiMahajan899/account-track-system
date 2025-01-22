CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS account (
    account_number BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance BIGINT NOT NULL,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE IF NOT EXISTS card (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(255) NOT NULL,
    card_type VARCHAR(255) NOT NULL,
--    balance DOUBLE NOT NULL,
    expiry_date DATE NOT NULL,
    account_id BIGINT,
    FOREIGN KEY (account_id) REFERENCES account(account_number)
);



INSERT INTO customer (id, name, email, phone_number)
VALUES (1, 'John Doe', 'johndoe@example.com', '123-456-7890');
INSERT INTO account (account_number, balance, customer_id)
VALUES (1, 5000.0, 1);
-- The customer_id should match the ID of the inserted user
INSERT INTO card (id, card_number, card_type, expiry_date, account_id)
VALUES (1, '1111-2222-3333-4444', 'DEBIT', '2027-06-30',1);
INSERT INTO card (id, card_number, card_type, expiry_date, account_id)
VALUES (2, '5555-6666-7777-8888', 'CREDIT', '2027-06-30',1);






INSERT INTO customer (id, name, email, phone_number)
VALUES (2, 'JSakshi', 'jsakhi@example.com', '123-456-7890');
INSERT INTO account (account_number, balance, customer_id)
VALUES (2, 5000.0, 2);
-- The customer_id should match the ID of the inserted user
INSERT INTO card (id, card_number, card_type, expiry_date, account_id)
VALUES (3, '1111-2222-3333-4444', 'DEBIT', '2027-06-30',2);
INSERT INTO card (id, card_number, card_type, expiry_date, account_id)
VALUES (4, '5555-6666-7777-8888', 'CREDIT', '2027-06-30',2);
