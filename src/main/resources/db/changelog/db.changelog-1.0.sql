--liquibase formatted sql

--changeset alexprokopiev:1
CREATE TABLE task (
    id SERIAL PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    status INT NOT NULL
);
