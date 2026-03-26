-- Drop sequence if exists
CREATE SCHEMA IF NOT EXISTS public;

DROP TABLE IF EXISTS public.productavailability;
DROP TABLE IF EXISTS public.products;
DROP TABLE IF EXISTS public.productavailability;
DROP SEQUENCE IF EXISTS public.products_seq;

DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'productsUser') THEN
      CREATE ROLE "productsUser"
      NOSUPERUSER
      NOCREATEDB
      NOCREATEROLE
      NOINHERIT
      LOGIN
      NOREPLICATION
      NOBYPASSRLS
      PASSWORD 'products';
   END IF;
END
$$;

-- Create sequence
CREATE SEQUENCE IF NOT EXISTS public.products_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1
    CACHE 1
    NO CYCLE;

-- Create products table
CREATE TABLE IF NOT EXISTS public.products (
    id BIGINT NOT NULL DEFAULT nextval('products_seq'),
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    created DATE NOT NULL,
    updated DATE NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (id)
);

-- Attach sequence to column
ALTER SEQUENCE public.products_seq OWNED BY products.id;

-- Indexes
CREATE INDEX products_id_idx ON public.products (id);
CREATE INDEX products_name_idx ON public.products (name);


-- Create productavailability table
CREATE TABLE IF NOT EXISTS public.productavailability (
    id UUID,
    product_id BIGINT,
    price NUMERIC,
    availability BOOLEAN,
    deleted BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT productavailability_products_fk
        FOREIGN KEY (product_id)
        REFERENCES public.products(id)
);


CREATE TABLE public.user_entity (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL,
    "role" VARCHAR(50) NOT NULL,
    CONSTRAINT user_entity_pk PRIMARY KEY (id)
);

-- Set owner

-- Index
CREATE INDEX productavailability_product_id_idx
    ON productavailability (product_id);

-- Permissions
--CREATE ROLE "productsUser" NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'products';

GRANT USAGE ON SCHEMA public TO "productsUser" ;

ALTER TABLE public.products OWNER TO "productsUser";
GRANT ALL PRIVILEGES ON TABLE products TO "productsUser";

ALTER TABLE public.productavailability OWNER TO "productsUser";
GRANT ALL PRIVILEGES ON TABLE productavailability TO "productsUser";

ALTER SEQUENCE public.products_seq OWNER TO "productsUser";
GRANT ALL PRIVILEGES ON SEQUENCE products_seq TO "productsUser";

-- Schema permissions
GRANT ALL ON SCHEMA public TO pg_database_owner;
GRANT USAGE ON SCHEMA public TO public;

ALTER TABLE public.user_entity OWNER TO "productsUser";

-- Grant permissions
GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES, TRIGGER ON TABLE public.user_entity TO "productsUser";
