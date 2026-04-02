-- liquibase formatted sql

-- changeset lakshman:1
CREATE TABLE wallets (
    id UUID PRIMARY KEY,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00
);
-- rollback DROP TABLE wallets;

-- changeset lakshman:2
-- Pre-seed a test wallet for initial verification
INSERT INTO wallets (id, balance) VALUES ('550e8400-e29b-41d4-a716-446655440000', 10000.00);
-- rollback DELETE FROM wallets WHERE id = '550e8400-e29b-41d4-a716-446655440000';
