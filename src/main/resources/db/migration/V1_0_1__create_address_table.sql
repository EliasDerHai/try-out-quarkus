CREATE TABLE address (
	id serial4 NOT NULL,
	street_name varchar NOT NULL,
	house_number varchar NOT NULL,
	zip_code integer NOT NULL,
	city varchar NOT NULL,
	latitude double precision NOT NULL,
	longitude double precision NOT NULL,
	CONSTRAINT address_pk PRIMARY KEY (id)
);

CREATE TABLE person (
	id varchar NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	phone_number varchar NULL,
	user_group varchar NOT NULL,
	CONSTRAINT person_pk PRIMARY KEY (id)
);

CREATE TABLE customer_order (
	id SERIAL4 NOT NULL,
    destination INTEGER NOT NULL,
    orderer VARCHAR NOT NULL,
    chef VARCHAR NOT NULL,
    delivery_driver VARCHAR NOT NULL,
    CONSTRAINT order_pk PRIMARY KEY (id),
    CONSTRAINT order_destination_fk
        FOREIGN KEY (destination)
        REFERENCES address(id),
    CONSTRAINT order_orderer_fk
        FOREIGN KEY (orderer)
        REFERENCES person(id),
    CONSTRAINT order_chef_fk
        FOREIGN KEY (chef)
        REFERENCES person(id),
    CONSTRAINT order_delivery_driver_fk
        FOREIGN KEY (delivery_driver)
        REFERENCES person(id)
);