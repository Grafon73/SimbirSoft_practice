package ru.simbirsoft.homework.author.service;

import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;

import java.util.List;


public interface AuthorService {
    /**
     * Список авторов
     */
    List<AuthorWithoutBooks> getAllAuthors();

    /**
     * Список книг автора
     */
    AuthorView listOfBooksByAuthor(AuthorWithoutBooks authorView);

    /**
     * Добавить автора
     */
    AuthorView addAuthor(AuthorView authorView);

    /**
     * Удалить автора
     */
    void removeAuthor(AuthorWithoutBooks authorView);
}
