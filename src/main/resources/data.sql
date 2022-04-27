INSERT INTO customer (name, username, adress)
VALUES ('Sara', 'theKitten', 'Rogsta 907'),
       ('Jimmy', 'KLM', 'Stöde 3'),
       ('Viktoria', 'brittV', 'Stationsvägen 30'),
       ('Ylva', 'Ylva-a', 'Stationsvägen 30');

INSERT INTO vehicle (name, model, rental_fee)
VALUES ('Red racer', 'Saab', 159.90),
       ('The blue nice one', 'Citroen', 200),
       ('Smaller blue one', 'Peugot', 50),
       ('Run-away truck', 'Kia', 8.9);

INSERT INTO booking (customer_Id, vehicle_Id, date)
VALUES (1, 2, '2022-01-02'),
       (3, 2, '2021-12-12'),
       (2, 3, '2023-12-13'),
       (0, 1, '2021-12-14'),
       (0, 0, '2021-12-15'),
       (2, 1, '2020-01-02');