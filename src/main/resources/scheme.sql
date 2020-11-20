CREATE TABLE IF NOT EXISTS genre     (
genre_id       INTEGER                  NOT NULL   COMMENT 'ID жанра' PRIMARY KEY AUTO_INCREMENT,
version        INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
genre_name     VARCHAR(50)              NOT NULL   COMMENT 'Название жанра',
create_date    TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date    TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время изменения записи'
);
COMMENT ON TABLE genre IS 'Жанр';

CREATE TABLE IF NOT EXISTS author     (
author_id      INTEGER                  NOT NULL   COMMENT 'ID автора' PRIMARY KEY AUTO_INCREMENT,
version        INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
first_name     VARCHAR(50)              NOT NULL   COMMENT 'Имя',
last_name      VARCHAR(50)              NOT NULL   COMMENT 'Фамилия',
middle_name    VARCHAR(50)                         COMMENT 'Отчество',
create_date    TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date    TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время изменения записи'
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
update_date     TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время изменения записи'
);
COMMENT ON TABLE person IS 'Человек';

CREATE TABLE IF NOT EXISTS book    (
book_id       INTEGER                  NOT NULL   COMMENT 'ID книги' PRIMARY KEY AUTO_INCREMENT,
version       INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
name          VARCHAR(50)                         COMMENT 'Название книги',
author_id     INTEGER                  NOT NULL   COMMENT 'ID автора',
create_date   TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date   TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время изменения записи'
);
COMMENT ON TABLE book IS 'Книга';

CREATE TABLE IF NOT EXISTS book_genre    (
book_id       INTEGER     NOT NULL   COMMENT 'ID книги',
genre_id      INTEGER     NOT NULL   COMMENT 'ID жанра'
);
COMMENT ON TABLE book IS 'Связь Книги с Жанром';


CREATE TABLE IF NOT EXISTS library_card  (
book_book_id       INTEGER                  NOT NULL   COMMENT 'ID книги',
person_person_id   INTEGER                  NOT NULL   COMMENT 'ID человека',
version            INTEGER                  NOT NULL   COMMENT 'Служебное поле hibernate',
create_date        TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время создания записи',
update_date        TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время изменения записи',
return_date        TIMESTAMP WITH TIME ZONE NOT NULL   COMMENT 'Время возврата книги'
);
COMMENT ON TABLE book IS 'Связь Книги с Жанром(библиотечная карточка)';