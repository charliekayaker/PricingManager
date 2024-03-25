ALTER TABLE prices
ADD CONSTRAINT fk_brand_id FOREIGN KEY (brand_id) REFERENCES brands (id);

ALTER TABLE prices
ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id);