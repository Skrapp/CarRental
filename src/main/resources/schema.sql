DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
    customer_Id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(30),
    username VARCHAR(30),
    adress VARCHAR(50)
);

DROP TABLE IF EXISTS vehicle;

CREATE TABLE vehicle (
    vehicle_Id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(30),
    model VARCHAR(30),
    rental_Fee DOUBLE
);

DROP TABLE IF EXISTS booking;

CREATE TABLE booking (
    booking_Id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    customer_Id BIGINT,
    vehicle_Id BIGINT,
    date DATE DEFAULT CURRENT_DATE
);
