package ru.simbirsoft.homework.author.service;

import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;

import java.util.Date;
import java.util.List;


public interface AuthorService {
    /**
     * Список авторов
     */
    List<AuthorWithoutBooks> getAllAuthors();

    /**
     * Список книг автора
     */
    AuthorView listOfBooksByAuthor(Integer id);

    /**
     * Добавить автора
     */
    AuthorView addAuthor(AuthorView authorView);

    /**
     * Удалить автора
     */
    void removeAuthor(Integer id);

    /**
     * Список авторов по ФИО и дате рождения
     */
    List<AuthorWithoutBooks> getAuthorsByBirthDateAndFIO(String firstName,
                                                         String lastName,
                                                         String middleName,
                                                         Date from,
                                                         Date to);

}
