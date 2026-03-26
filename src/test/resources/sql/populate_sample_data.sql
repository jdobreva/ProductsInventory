DO '

DECLARE

	the_next_prod_id BIGINT;
    
BEGIN

DELETE FROM productavailability;
DELETE FROM products;
DELETE FROM user_entity;

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

INSERT INTO user_entity ("password", username, "role") VALUES
(''$2a$10$wA2VO2/LQE8UhVad3c/75e7/LLDJHXdZnoPCfRjXKCy0lzEWw8WcO'', ''test'', ''USER'');

END;

' LANGUAGE plpgsql;
