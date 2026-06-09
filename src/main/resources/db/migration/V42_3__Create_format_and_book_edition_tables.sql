DROP TABLE IF EXISTS book_copy;
DROP TYPE IF EXISTS copy_status;
DROP TYPE IF EXISTS copy_condition;

CREATE TABLE IF NOT EXISTS format
(
    id           VARCHAR DEFAULT gen_random_uuid()
        CONSTRAINT format_pk PRIMARY KEY,
    format_label VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS book_edition
(
    id        VARCHAR DEFAULT gen_random_uuid()
        CONSTRAINT book_edition_pk PRIMARY KEY,
    isbn      VARCHAR UNIQUE,
    book_id   BIGINT NOT NULL
        CONSTRAINT book_edition_book_fk REFERENCES book (id),
    format_id VARCHAR
        CONSTRAINT book_edition_format_fk REFERENCES format (id),
    price     DOUBLE PRECISION
);
