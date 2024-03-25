INSERT INTO brands (name,created_at,updated_at) VALUES ('Zara', now(), now());

INSERT INTO products (id, name,description,code,created_at,updated_at)
VALUES (35455, 'PANTALON CHUPIN MORADO','Este pantalon es lo mas top.','ABC111',now(),now());

INSERT INTO price_lists (description, rate, created_at, updated_at)
VALUES ('Precio tarifa 1', 0.5, now(), now()),
    ('Precio tarifa 2', 0.75, now(), now()),
    ('Precio tarifa 3', 0.9, now(), now()),
    ('Precio tarifa 4', 1.25, now(), now());

INSERT INTO prices (brand_id, start_date, end_date, price_list_id, product_id, priority, price, currency, created_at, updated_at)
VALUES (1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.5, 'EUR', now(), now()),
	(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR', now(), now()),
    (1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.5, 'EUR', now(), now()),
    (1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR', now(), now());
