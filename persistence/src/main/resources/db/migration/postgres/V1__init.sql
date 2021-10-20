CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE document (
    id bigint,
    uuid UUID,
    title varchar(255),
    path varchar(255),
    created TIMESTAMP,
    changed TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE meta (
    id bigint,
    document_id bigint,
    document_created TIMESTAMP,
    description TEXT,
    created TIMESTAMP,
    changed TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (document_id)
        REFERENCES document (id)
);