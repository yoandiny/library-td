CREATE TABLE IF NOT EXISTS customer
(
    id          UUID DEFAULT gen_random_uuid()
        CONSTRAINT customer_pk PRIMARY KEY,
    full_name   VARCHAR NOT NULL,
    email       VARCHAR NOT NULL UNIQUE,
    phone       VARCHAR,
    register_at TIMESTAMP
);