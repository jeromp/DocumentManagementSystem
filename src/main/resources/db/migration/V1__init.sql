CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE DOCUMENT (
    id uuid DEFAULT uuid_generate_v4 (),
    title varchar(255),
    path varchar(255),
    created TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE META (
    id uuid DEFAULT uuid_generate_v4 (),
    document_id UUID,
    meta_key varchar(255),
    meta_value TEXT,
    created TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (document_id)
        REFERENCES DOCUMENT (id)
);