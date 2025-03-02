-- create a table
CREATE TABLE test
(
    id       INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name     TEXT    NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE
);