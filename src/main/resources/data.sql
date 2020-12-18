INSERT INTO users (username, password, enabled) VALUES
('admin','$argon2id$v=19$m=4096,t=3,p=1$BdkuTidF6zojmLfm4TqLPg$jPGu3IhI0oxGrdfYpryPtOhDb2+DY/sLiVA9UpAxGa0',1),
('1','$argon2id$v=19$m=4096,t=3,p=1$IyGvKDwO+DlxyFWYR1dmxQ$OAdLMgG26ir2zdW9hwwN8M2KISsEDSkjx5YyigkZkdc',1),
('2','$argon2id$v=19$m=4096,t=3,p=1$cMFir4bssbe0HDY64OupXg$DLy2J7WBLf9yb0ANd+aIcxSGLhCqEVktsGKETZ0w/Pg',1),
('3','$argon2id$v=19$m=4096,t=3,p=1$x8h0CYaNH2Q9t+kFqCyvTQ$E0AVOLvWt5Eot/s096Ee+NCSEIrJK4+jRGngaXNobu0',1),
('4','$argon2id$v=19$m=4096,t=3,p=1$ye9LOVp/M/J7BUP9IB5CWQ$CS1Bh41XdhebiD/tG4dmm/x+RhI+cYpirzyBgJ4w2mc',1);

INSERT INTO authorities (username, authority) VALUES
('admin', 'ROLE_ADMIN'),
('1', 'ROLE_USER'),
('2', 'ROLE_USER'),
('3', 'ROLE_USER'),
('4', 'ROLE_USER');

INSERT INTO AUTHOR(VERSION, FIRST_NAME, LAST_NAME, MIDDLE_NAME, BIRTH_DATE, CREATE_DATE, UPDATE_DATE) VALUES
(0, 'Илья', 'Бугынин', 'Витальевич', '2019-11-19', now(), null),
(0, 'Дмитрий', 'Ипастов', 'Олегович', '2020-11-19', now(),null),
(0, 'Виталий', 'Горин', 'Александрович', '2018-11-19',now(), null),
(0, 'Олег', 'Резин', 'Степанович', '2017-11-19', now(), null),
(0, 'Иван', 'Бочкин', 'Евгеньевич', '2016-11-19', now(), null);

INSERT INTO GENRE(VERSION,GENRE_NAME, CREATE_DATE, UPDATE_DATE) VALUES
(0, 'Ужасы', now(), null),
(0, 'Фантастика', now(), null),
(0, 'Приключения', now(), null),
(0, 'Драма', now(), null),
(0, 'Детектив',now(), null);

INSERT INTO PERSON(VERSION, BIRTH_DATE, FIRST_NAME, LAST_NAME, MIDDLE_NAME, CREATE_DATE, UPDATE_DATE, USERNAME) VALUES
(0, '2001-01-01', 'Олег', 'Резин', 'Степанович', now(), null,'1'),
(0, '2001-01-01', 'Иван', 'Бочкин', 'Евгеньевич', now(), null,'2'),
(0, '2001-01-01', 'Дмитрий', 'Королев', 'Иванович', now(), null,'3'),
(0, '2001-01-01', 'Егор', 'Лапин', 'Валерьевич', now(), null,'4'),
(0, '2001-01-01', 'Александр', 'Белугин', 'Александрович', now(), null,'admin');

INSERT INTO BOOK (VERSION, NAME, AUTHOR_ID, ADMISSION_DATE, PUBLICATION_DATE, CREATE_DATE, UPDATE_DATE) VALUES
(0, 'Удивительное приключение',1,'2020-11-19','2020-11-18', now(), null),
(0, 'Страна чудес',2,'2020-11-19','2019-11-18', now(),null),
(0, 'Битва',3,'2020-11-19','2018-11-18', now(), null),
(0, 'За горизонтом',4,'2020-11-19','2017-11-18', now(), null),
(0, 'Светлое будущее',3,'2020-11-19','2016-11-18', now(), null),
(0, 'Цвет красок',1,'2020-11-19','2015-11-18', now(), null),
(0, 'Земля будущего',5,'2020-11-19','2014-11-18', now(), null);

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

INSERT INTO LIBRARY_CARD(CARD_ID, BOOK_BOOK_ID, PERSON_PERSON_ID,VERSION, CREATE_DATE, RETURN_DATE, UPDATE_DATE, IN_LIBRARY) VALUES
(1,1,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-18T08:51:52.447763100+04:00[Europe/Samara]',null,true),
(2,2,2,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-18T08:51:52.447763100+04:00[Europe/Samara]',null,true),
(3,3,3,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null,true),
(4,6,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null,true),
(5,7,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null,true),
(6,1,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-18T08:51:52.447763100+04:00[Europe/Samara]',null,false),
(7,2,2,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-18T08:51:52.447763100+04:00[Europe/Samara]',null,false),
(8,3,3,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null,false),
(9,6,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null,false),
(10,7,1,0,'2020-11-19T08:51:52.447763100+04:00[Europe/Samara]','2020-11-26T08:51:52.447763100+04:00[Europe/Samara]',null,false);




