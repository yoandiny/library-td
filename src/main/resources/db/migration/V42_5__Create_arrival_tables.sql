CREATE TABLE IF NOT EXISTS arrival
(
    id         UUID DEFAULT gen_random_uuid()
        CONSTRAINT arrival_pk PRIMARY KEY,
    arrived_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS arrival_line
(
    id              UUID DEFAULT gen_random_uuid()
        CONSTRAINT arrival_line_pk PRIMARY KEY,
    arrival_id      UUID NOT NULL
        CONSTRAINT arrival_line_arrival_fk REFERENCES arrival (id) ON DELETE CASCADE,
    book_edition_id UUID NOT NULL
        CONSTRAINT arrival_line_book_edition_fk REFERENCES book_edition (id),
    quantity        INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS arrival_line_book_edition_idx
    ON arrival_line (book_edition_id);
