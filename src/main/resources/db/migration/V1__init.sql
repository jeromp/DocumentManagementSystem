CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE document (
    id uuid DEFAULT uuid_generate_v4 (),
    title varchar(255),
    path varchar(255),
    created TIMESTAMP,
    changed TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE meta (
    document_id UUID,
    document_created TIMESTAMP,
    description TEXT,
    created TIMESTAMP,
    changed TIMESTAMP,
    PRIMARY KEY (document_id),
    FOREIGN KEY (document_id)
        REFERENCES document (id)
);