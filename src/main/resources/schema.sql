DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS genre     (
genre_id       INTEGER                  NOT NULL   COMMENT 'ID жанра' PRIMARY KEY AUTO_INCREMENT,
version        INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
genre_name     VARCHAR(50)              NOT NULL   COMMENT 'Название жанра',
create_date    TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date    TIMESTAMP WITH TIME ZONE            COMMENT 'Время изменения записи'
);
COMMENT ON TABLE genre IS 'Жанр';

CREATE TABLE IF NOT EXISTS author     (
author_id      INTEGER                  NOT NULL   COMMENT 'ID автора' PRIMARY KEY AUTO_INCREMENT,
version        INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
first_name     VARCHAR(50)              NOT NULL   COMMENT 'Имя',
last_name      VARCHAR(50)              NOT NULL   COMMENT 'Фамилия',
middle_name    VARCHAR(50)                         COMMENT 'Отчество',
birth_date     DATE                                COMMENT 'Дата рождения',
create_date    TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date    TIMESTAMP WITH TIME ZONE            COMMENT 'Время изменения записи'
);
COMMENT ON TABLE author IS 'Автор';

CREATE TABLE IF NOT EXISTS person     (
person_id       INTEGER                  NOT NULL   COMMENT 'ID человека' PRIMARY KEY AUTO_INCREMENT,
version         INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
birth_date      DATE                                COMMENT 'Дата рождения',
first_name      VARCHAR(50)              NOT NULL   COMMENT 'Имя',
last_name       VARCHAR(50)              NOT NULL   COMMENT 'Фамилия',
middle_name     VARCHAR(50)                         COMMENT 'Отчество',
create_date     TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date     TIMESTAMP WITH TIME ZONE            COMMENT 'Время изменения записи'
);
COMMENT ON TABLE person IS 'Человек';

CREATE TABLE IF NOT EXISTS book    (
book_id             INTEGER                  NOT NULL   COMMENT 'ID книги' PRIMARY KEY AUTO_INCREMENT,
version             INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
name                VARCHAR(50)                         COMMENT 'Название книги',
author_id           INTEGER                  NOT NULL   COMMENT 'ID автора',
admission_date      DATE                                COMMENT 'Дата поступления',
publication_date    DATE                                COMMENT 'Дата издания',
create_date         TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date         TIMESTAMP WITH TIME ZONE            COMMENT 'Время изменения записи'
);
COMMENT ON TABLE book IS 'Книга';

CREATE TABLE IF NOT EXISTS book_genre    (
book_id       INTEGER     NOT NULL   COMMENT 'ID книги',
genre_id      INTEGER     NOT NULL   COMMENT 'ID жанра'
);
COMMENT ON TABLE book IS 'Связь Книги с Жанром';


CREATE TABLE IF NOT EXISTS library_card  (
card_id            INTEGER                  NOT NULL   COMMENT 'ID записи' PRIMARY KEY AUTO_INCREMENT,
book_book_id       INTEGER                  NOT NULL   COMMENT 'ID книги',
person_person_id   INTEGER                  NOT NULL   COMMENT 'ID человека',
version            INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
create_date        TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date        TIMESTAMP WITH TIME ZONE            COMMENT 'Время изменения записи',
return_date        TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время возврата книги',
in_library         BOOLEAN                             COMMENT 'Находится ли книга в библиотеке'
);
COMMENT ON TABLE book IS 'Связь Книги с Жанром(библиотечная карточка)';

CREATE TABLE IF NOT EXISTS users (
username VARCHAR(50) NOT NULL,
password VARCHAR(100) NOT NULL,
enabled TINYINT NOT NULL DEFAULT 1,
PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities (
username VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
    on authorities (username,authority);

