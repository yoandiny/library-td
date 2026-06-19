-- SALE table
CREATE TABLE IF NOT EXISTS sale
(
    id           UUID DEFAULT gen_random_uuid()
        CONSTRAINT sale_pk PRIMARY KEY,
    id_customer  UUID NOT NULL
        CONSTRAINT sale_customer_fk REFERENCES customer (id),
    sale_date    TIMESTAMP NOT NULL,
    total_amount NUMERIC(15, 2) NOT NULL DEFAULT 0,
    status       VARCHAR(20) NOT NULL
        CONSTRAINT sale_status_check CHECK (status IN ('IN_PROGRESS', 'VALIDATED', 'CANCELLED'))
);

-- SALE_LINE table
CREATE TABLE IF NOT EXISTS sale_line
(
    id              UUID DEFAULT gen_random_uuid()
        CONSTRAINT sale_line_pk PRIMARY KEY,
    id_sale         UUID NOT NULL
        CONSTRAINT sale_line_sale_fk REFERENCES sale (id),
    id_book_edition UUID NOT NULL
        CONSTRAINT sale_line_book_edition_fk REFERENCES book_edition (id),
    unit_price      NUMERIC(15, 2) NOT NULL,
    quantity        INTEGER NOT NULL CONSTRAINT sale_line_quantity_positive CHECK (quantity > 0)
);

-- PAYMENT table
CREATE TABLE IF NOT EXISTS payment
(
    id      UUID DEFAULT gen_random_uuid()
        CONSTRAINT payment_pk PRIMARY KEY,
    id_sale UUID NOT NULL UNIQUE
        CONSTRAINT payment_sale_fk REFERENCES sale (id),
    amount  NUMERIC(15, 2) NOT NULL,
    method  VARCHAR(20) NOT NULL
        CONSTRAINT payment_method_check CHECK (method IN ('CASH', 'CARD', 'MOBILE')),
    status  VARCHAR(20) NOT NULL
        CONSTRAINT payment_status_check CHECK (status IN ('PENDING', 'PAID', 'FAILED')),
    paid_at TIMESTAMP
);