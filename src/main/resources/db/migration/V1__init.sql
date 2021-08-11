CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE FILE (
    id uuid DEFAULT uuid_generate_v4 (),
    title varchar(255),
    path varchar(255),
    created TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE META (
    id uuid DEFAULT uuid_generate_v4 (),
    file_id UUID,
    meta_key varchar(255),
    meta_value TEXT,
    created TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (file_id)
        REFERENCES FILE (id)
);