INSERT INTO MOVIE (movieId, name, rating)
VALUES (1, 'Sholay', 5),
(2, 'Roja', 5),
(3, 'Jurassic Park', 4),
(4, 'Titanic', 5),
(5, 'Black Panther', 3),
(6, 'Cast Away', 4);

INSERT INTO THEATER(theaterId, name, city)
VALUES (1, 'AMC', 'Cincinnati'),
(2, 'AMC', 'Dayton'),
(3, 'AMC', 'Columbus'),
(4, 'Royal', 'Columbus'),
(5, 'Royal', 'Dayton'),
(6, 'IMax', 'Cincinnati');

INSERT INTO PRICE(movieId, theaterId, price)
    VALUES (1, 2, 250.00),
(3, 2, 350.00),
(4, 2, 200.00),
(2, 1, 150.00),
(5, 4, 350.00),
(5, 3, 120.00),
(6, 3, 220.00);






