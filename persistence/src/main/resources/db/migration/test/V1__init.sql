CREATE TABLE document (
    id bigint,
    uuid uuid,
    parent_id bigint,
    type varchar(255),
    title varchar(255),
    path varchar(255),
    created TIMESTAMP,
    changed TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id)
        REFERENCES document (id)
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