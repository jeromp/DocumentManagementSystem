ALTER TABLE document
ADD COLUMN parent_id bigint,
ADD COLUMN type varchar(255),
ADD CONSTRAINT fk_parent_id FOREIGN KEY (parent_id) REFERENCES document (id);


