CREATE TYPE copy_status AS ENUM ('AVAILABLE', 'BORROWED', 'RESERVED', 'LOST', 'DAMAGED');
CREATE TYPE copy_condition AS ENUM ('NEW', 'GOOD', 'FAIR', 'POOR');

CREATE TABLE IF NOT EXISTS book_copy
(
    id        VARCHAR DEFAULT gen_random_uuid()
        CONSTRAINT book_copy_pk PRIMARY KEY,
    book_id   BIGINT      NOT NULL
        CONSTRAINT book_copy_book_fk REFERENCES book (id),
    barcode   VARCHAR(100) UNIQUE,
    condition VARCHAR(50),
    status    VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    location  VARCHAR(255),
    added_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);