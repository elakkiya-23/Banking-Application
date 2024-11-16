-- Drop and recreate the demo_bank database (for fresh start)
DROP DATABASE IF EXISTS demo_bank;
CREATE DATABASE IF NOT EXISTS demo_bank;

-- Use the demo_bank database
USE demo_bank;

-- USERS TABLE STRUCTURE:
CREATE TABLE IF NOT EXISTS users (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255) NULL,
    code INT NULL,
    verified INT DEFAULT 0,
    verified_at DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ACCOUNT TABLE STRUCTURE:
DROP TABLE IF EXISTS accounts;
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    account_number INT NOT NULL,
    account_name VARCHAR(50) DEFAULT "Base_account",
    account_type VARCHAR(50) DEFAULT "Savings",
    balance DECIMAL(18,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- TRANSACTION HISTORY TABLE STRUCTURE:
DROP TABLE IF EXISTS transaction_history; 
CREATE TABLE IF NOT EXISTS transaction_history (
    transaction_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    transaction_type VARCHAR(20),
    amount DECIMAL(18,2),
    source VARCHAR(20),
    status VARCHAR(20),
    reason_code VARCHAR(255) DEFAULT "Transaction was successful",
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- PAYMENTS TABLE STRUCTURE:
DROP TABLE IF EXISTS payments;
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    beneficiary VARCHAR(50),
    beneficiary_acc_no VARCHAR(255),
    amount DECIMAL(18,2),
    reference_no VARCHAR(50) DEFAULT "General Payment",
    status VARCHAR(20),
    reason_code VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- CREATE TRANSACTION HISTORY VIEW:
DROP VIEW IF EXISTS v_transaction_history;
CREATE VIEW IF NOT EXISTS v_transaction_history AS
SELECT 
    t.transaction_id,
    a.account_id,
    a.account_name,
    u.user_id,
    t.transaction_type,
    t.amount,
    t.source,
    t.status,
    t.reason_code,
    t.created_at
FROM
    transaction_history AS t
INNER JOIN 
    accounts AS a ON t.account_id = a.account_id 
INNER JOIN 
    users AS u ON a.customer_id = u.user_id;

-- CREATE PAYMENTS HISTORY VIEW:
DROP VIEW IF EXISTS v_payments;
CREATE VIEW IF NOT EXISTS v_payments AS
SELECT 
    p.payment_id,
    a.account_id,
    u.user_id,
    p.beneficiary,
    p.beneficiary_acc_no,
    p.amount,
    p.status,
    p.reference_no,
    p.reason_code,
    p.created_at
FROM 
    payments AS p
INNER JOIN
    accounts AS a ON p.account_id = a.account_id
INNER JOIN
    users AS u ON a.customer_id = u.user_id;

-- View the transaction history and payments:
SELECT * FROM v_transaction_history;
SELECT * FROM v_payments;

SHOW TABLES;


-- Example Query to check balance for customer 2
-- SELECT SUM(balance) FROM accounts WHERE customer_id = 2;
