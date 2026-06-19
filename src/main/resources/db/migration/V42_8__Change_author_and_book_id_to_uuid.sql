-- V42_8__Change_author_and_book_id_to_uuid.sql

-- Nécessaire pour gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================================================
-- 1. AUTHOR : ajout d'une colonne uuid temporaire
-- =========================================================
ALTER TABLE author
    ADD COLUMN uuid_id UUID NOT NULL DEFAULT gen_random_uuid();

-- =========================================================
-- 2. BOOK : ajout d'une colonne uuid temporaire pour id et author_id
-- =========================================================
ALTER TABLE book
    ADD COLUMN uuid_id UUID NOT NULL DEFAULT gen_random_uuid();

ALTER TABLE book
    ADD COLUMN uuid_author_id UUID;

-- Remplir uuid_author_id à partir de la correspondance author.id -> author.uuid_id
UPDATE book b
SET uuid_author_id = a.uuid_id
FROM author a
WHERE b.author_id = a.id;

ALTER TABLE book
    ALTER COLUMN uuid_author_id SET NOT NULL;

-- =========================================================
-- 3. Suppression des anciennes contraintes (FK puis PK)
-- =========================================================
ALTER TABLE book DROP CONSTRAINT book_author_fk;
ALTER TABLE book DROP CONSTRAINT book_pk;
ALTER TABLE author DROP CONSTRAINT author_pk;

-- =========================================================
-- 4. Suppression des anciennes colonnes BIGINT
-- =========================================================
ALTER TABLE book DROP COLUMN author_id;
ALTER TABLE book DROP COLUMN id;
ALTER TABLE author DROP COLUMN id;

-- =========================================================
-- 5. Renommage des colonnes uuid en id / author_id
-- =========================================================
ALTER TABLE author RENAME COLUMN uuid_id TO id;
ALTER TABLE book RENAME COLUMN uuid_id TO id;
ALTER TABLE book RENAME COLUMN uuid_author_id TO author_id;

-- =========================================================
-- 6. Recréation des contraintes PK / FK avec génération auto par défaut
-- =========================================================
ALTER TABLE author
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE author
    ADD CONSTRAINT author_pk PRIMARY KEY (id);

ALTER TABLE book
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE book
    ADD CONSTRAINT book_pk PRIMARY KEY (id);

ALTER TABLE book
    ADD CONSTRAINT book_author_fk FOREIGN KEY (author_id) REFERENCES author (id);