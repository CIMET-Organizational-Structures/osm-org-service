CREATE TABLE contributor
(
    id   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    type VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE repo
(
    id   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255)
);

CREATE TABLE repo_contributor
(
    repo_id  BIGINT,
    contributor_id BIGINT,
    PRIMARY KEY (repo_id, contributor_id),
    FOREIGN KEY (repo_id) REFERENCES repo (id),
    FOREIGN KEY (contributor_id) REFERENCES contributor (id)
);