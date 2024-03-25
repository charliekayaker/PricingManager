CREATE TABLE brands (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL
);

CREATE TABLE price_lists (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  description varchar(2000) NOT NULL,
  rate decimal(11,2) NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL
);

CREATE TABLE products (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  description text NOT NULL,
  code varchar(50),
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL
);

CREATE TABLE prices (
  id bigint PRIMARY KEY,
  brand_id bigint NOT NULL,
  start_date timestamp NOT NULL,
  end_date timestamp NOT NULL,
  price_list_id bigint NOT NULL,
  product_id bigint NOT NULL,
  priority integer NOT NULL,
  price decimal(11,2) NOT NULL,
  currency varchar(5) NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp NOT NULL
);
