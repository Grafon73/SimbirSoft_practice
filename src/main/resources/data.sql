INSERT INTO AUTHOR(VERSION, FIRST_NAME, LAST_NAME, MIDDLE_NAME, CREATE_DATE, UPDATE_DATE) VALUES
(0, 'Илья', 'Бугынин', 'Витальевич', now(), null),
(0, 'Дмитрий', 'Ипастов', 'Олегович', now(),null),
(0, 'Виталий', 'Горин', 'Александрович',now(), null),
(0, 'Олег', 'Резин', 'Степанович', now(), null),
(0, 'Иван', 'Бочкин', 'Евгеньевич', now(), null);

INSERT INTO GENRE(VERSION,GENRE_NAME, CREATE_DATE, UPDATE_DATE) VALUES
(0, 'Ужасы', now(), null),
(0, 'Фантастика', now(), null),
(0, 'Приключения', now(), null),
(0, 'Драма', now(), null),
(0, 'Детектив',now(), null);

INSERT INTO PERSON(VERSION, BIRTH_DATE, FIRST_NAME, LAST_NAME, MIDDLE_NAME, CREATE_DATE, UPDATE_DATE) VALUES
(0, '2001-01-01', 'Олег', 'Резин', 'Степанович', now(), null),
(0, '2001-01-01', 'Иван', 'Бочкин', 'Евгеньевич', now(), null),
(0, '2001-01-01', 'Дмитрий', 'Королев', 'Иванович', now(), null),
(0, '2001-01-01', 'Егор', 'Лапин', 'Валерьевич', now(), null),
(0, '2001-01-01', 'Александр', 'Белугин', 'Александрович', now(), null);

INSERT INTO BOOK (VERSION, NAME, AUTHOR_ID, CREATE_DATE, UPDATE_DATE) VALUES
(0, 'Удивительное приключение',1, now(), null),
(0, 'Страна чудес',2, now(),null),
(0, 'Битва',3, now(), null),
(0, 'За горизонтом',4, now(), null),
(0, 'Светлое будущее',3, now(), null),
(0, 'Цвет красок',1, now(), null),
(0, 'Земля будущего',5, now(), null);

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

INSERT INTO LIBRARY_CARD(BOOK_BOOK_ID, PERSON_PERSON_ID,VERSION, CREATE_DATE, RETURN_DATE, UPDATE_DATE) VALUES
(1,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-18T08:51:52.447763100+04:00[Europe/Samara]',null),
(2,2,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-18T08:51:52.447763100+04:00[Europe/Samara]',null),
(3,3,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null),
(6,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null),
(7,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null);