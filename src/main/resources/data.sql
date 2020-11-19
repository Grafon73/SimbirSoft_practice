INSERT INTO AUTHOR(VERSION, FIRST_NAME, LAST_NAME, MIDDLE_NAME) VALUES
(0, 'Илья', 'Бугынин', 'Витальевич'),
(0, 'Дмитрий', 'Ипастов', 'Олегович'),
(0, 'Виталий', 'Горин', 'Александрович'),
(0, 'Олег', 'Резин', 'Степанович'),
(0, 'Иван', 'Бочкин', 'Евгеньевич');

INSERT INTO GENRE(VERSION,GENRE_NAME) VALUES
(0, 'Ужасы'),
(0, 'Фантастика'),
(0, 'Приключения'),
(0, 'Драма'),
(0, 'Детектив');

INSERT INTO PERSON(VERSION, BIRTH_DATE, FIRST_NAME, LAST_NAME, MIDDLE_NAME) VALUES
(0, '2001-01-01', 'Олег', 'Резин', 'Степанович'),
(0, '2001-01-01', 'Иван', 'Бочкин', 'Евгеньевич'),
(0, '2001-01-01', 'Дмитрий', 'Королев', 'Иванович'),
(0, '2001-01-01', 'Егор', 'Лапин', 'Валерьевич'),
(0, '2001-01-01', 'Александр', 'Белугин', 'Александрович');

INSERT INTO BOOK (VERSION, NAME, AUTHOR_ID, PERSON_ID) VALUES
(0, 'Удивительное приключение',1,1),
(0, 'Страна чудес',2,2),
(0, 'Битва',3,3),
(0, 'За горизонтом',4,null),
(0, 'Светлое будущее',3,null),
(0, 'Цвет красок',1,1),
(0, 'Земля будущего',5,1);

INSERT INTO BOOK_GENRE  (BOOK_ID, GENRE_ID) VALUES
(1,1),
(1,2),
(1,3),
(2,2),
(2,1),
(3,1),
(4,1),
(3,3),
(4,4),
(5,5),
(6,1),
(7,1);
