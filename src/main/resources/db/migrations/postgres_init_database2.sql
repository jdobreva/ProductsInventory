DROP SEQUENCE products_seq;

CREATE SEQUENCE products_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- Permissions

ALTER SEQUENCE products_seq OWNER TO "productsAdmin";
GRANT ALL ON SEQUENCE products_seq TO "productsAdmin";

-- public.products definition
-- Drop table

-- DROP TABLE products;

-- CREATE TABLE products ( id int8 NOT NULL, "name" varchar(50) NOT NULL, description varchar(255) NULL, created timestamp NOT NULL, updated timestamp NOT NULL, CONSTRAINT product_pk PRIMARY KEY (id));
-- CREATE INDEX products_id_idx ON products USING btree (id);
-- CREATE INDEX products_name_idx ON products USING btree (name);

-- Permissions

-- ALTER TABLE products OWNER TO "productsAdmin";
-- GRANT ALL ON TABLE products TO "productsAdmin";


-- public.productavailability definition

-- Drop table

-- DROP TABLE productavailability;

-- CREATE TABLE productavailability ( product_id int8 NULL, price numeric NULL, availability bool NULL, deleted bool DEFAULT false NOT NULL, id uuid NULL, CONSTRAINT productavailability_products_fk FOREIGN KEY (product_id) REFERENCES public.products(id));
-- CREATE INDEX productavailability_product_id_idx ON productavailability USING btree (product_id);

-- Permissions

-- ALTER TABLE productavailability OWNER TO "productsAdmin";
-- GRANT ALL ON TABLE productavailability TO "productsAdmin";

-- Permissions
-- GRANT ALL ON SCHEMA public TO pg_database_owner;
-- GRANT USAGE ON SCHEMA public TO public;
-- ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public GRANT UPDATE, DELETE, INSERT, SELECT ON TABLES TO "productsAdmin";