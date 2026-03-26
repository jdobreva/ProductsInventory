DO '

DECLARE

	the_next_prod_id BIGINT;
    
BEGIN

DELETE FROM productavailability;
DELETE FROM products;

PERFORM setval(''products_seq'', 1, false);

the_next_prod_id := nextval(''products_seq'');

INSERT INTO products (id, name, description, created, updated)
VALUES (
	the_next_prod_id,
	''product4'',
	''description4'',
	''2026-03-12 00:00:00'',
	''2026-03-12 00:00:00''
);

INSERT INTO productavailability (product_id, price, availability, deleted, id)
VALUES (
	the_next_prod_id,
	124.60,
	true,
	false,
	gen_random_uuid()
);

END;

' LANGUAGE plpgsql;