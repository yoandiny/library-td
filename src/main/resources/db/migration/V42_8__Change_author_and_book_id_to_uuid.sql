-- V42_8__Change_author_and_book_id_to_uuid.sql

CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO $$
DECLARE
    author_id_type   TEXT;
    book_id_type     TEXT;
    book_edition_exists BOOLEAN;
BEGIN
    SELECT data_type INTO author_id_type
    FROM information_schema.columns
    WHERE table_name = 'author' AND column_name = 'id';

    SELECT data_type INTO book_id_type
    FROM information_schema.columns
    WHERE table_name = 'book' AND column_name = 'id';

    -- Si déjà converti (ex: fait manuellement), on ne fait rien
    IF author_id_type = 'uuid' AND book_id_type = 'uuid' THEN
        RAISE NOTICE 'author.id et book.id sont déjà en UUID, migration ignorée.';
        RETURN;
    END IF;

    -- =========================================================
    -- 1. AUTHOR : colonne uuid temporaire
    -- =========================================================
    EXECUTE 'ALTER TABLE author ADD COLUMN uuid_id UUID NOT NULL DEFAULT gen_random_uuid()';

    -- =========================================================
    -- 2. BOOK : colonnes uuid temporaires
    -- =========================================================
    EXECUTE 'ALTER TABLE book ADD COLUMN uuid_id UUID NOT NULL DEFAULT gen_random_uuid()';
    EXECUTE 'ALTER TABLE book ADD COLUMN uuid_author_id UUID';

    EXECUTE '
        UPDATE book b
        SET uuid_author_id = a.uuid_id
        FROM author a
        WHERE b.author_id = a.id';

    EXECUTE 'ALTER TABLE book ALTER COLUMN uuid_author_id SET NOT NULL';

    -- =========================================================
    -- 3. BOOK_EDITION : colonne uuid temporaire pour book_id
    --    (uniquement si la table existe et que book_id n'est pas déjà uuid)
    -- =========================================================
    SELECT EXISTS (
        SELECT 1 FROM information_schema.tables WHERE table_name = 'book_edition'
    ) INTO book_edition_exists;

    IF book_edition_exists THEN
        EXECUTE 'ALTER TABLE book_edition ADD COLUMN uuid_book_id UUID';

        EXECUTE '
            UPDATE book_edition be
            SET uuid_book_id = b.uuid_id
            FROM book b
            WHERE be.book_id = b.id';

        EXECUTE 'ALTER TABLE book_edition ALTER COLUMN uuid_book_id SET NOT NULL';

        EXECUTE 'ALTER TABLE book_edition DROP CONSTRAINT IF EXISTS book_edition_book_id_fkey';
        EXECUTE 'ALTER TABLE book_edition DROP COLUMN book_id';
        EXECUTE 'ALTER TABLE book_edition RENAME COLUMN uuid_book_id TO book_id';
    END IF;

    -- =========================================================
    -- 4. Suppression des anciennes contraintes
    -- =========================================================
    EXECUTE 'ALTER TABLE book DROP CONSTRAINT IF EXISTS book_author_id_fkey';
    EXECUTE 'ALTER TABLE book DROP CONSTRAINT IF EXISTS book_pkey';
    EXECUTE 'ALTER TABLE author DROP CONSTRAINT IF EXISTS author_pkey';

    -- =========================================================
    -- 5. Suppression des anciennes colonnes BIGINT
    -- =========================================================
    EXECUTE 'ALTER TABLE book DROP COLUMN author_id';
    EXECUTE 'ALTER TABLE book DROP COLUMN id';
    EXECUTE 'ALTER TABLE author DROP COLUMN id';

    -- =========================================================
    -- 6. Renommage des colonnes uuid
    -- =========================================================
    EXECUTE 'ALTER TABLE author RENAME COLUMN uuid_id TO id';
    EXECUTE 'ALTER TABLE book RENAME COLUMN uuid_id TO id';
    EXECUTE 'ALTER TABLE book RENAME COLUMN uuid_author_id TO author_id';

    -- =========================================================
    -- 7. Recréation des contraintes
    -- =========================================================
    EXECUTE 'ALTER TABLE author ALTER COLUMN id SET DEFAULT gen_random_uuid()';
    EXECUTE 'ALTER TABLE author ADD CONSTRAINT author_pkey PRIMARY KEY (id)';

    EXECUTE 'ALTER TABLE book ALTER COLUMN id SET DEFAULT gen_random_uuid()';
    EXECUTE 'ALTER TABLE book ADD CONSTRAINT book_pkey PRIMARY KEY (id)';

    EXECUTE 'ALTER TABLE book ADD CONSTRAINT book_author_id_fkey FOREIGN KEY (author_id) REFERENCES author (id)';

    IF book_edition_exists THEN
        EXECUTE 'ALTER TABLE book_edition ADD CONSTRAINT book_edition_book_id_fkey FOREIGN KEY (book_id) REFERENCES book (id)';
    END IF;

    RAISE NOTICE 'Conversion BIGINT -> UUID terminée avec succès.';
END $$;