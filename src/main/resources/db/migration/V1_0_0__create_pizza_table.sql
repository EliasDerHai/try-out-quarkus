CREATE TABLE pizza (
	id serial4 NOT NULL,
	name varchar NOT NULL,
	description varchar NULL,
	price integer NOT NULL,
	CONSTRAINT pizza_pk PRIMARY KEY (id)
);