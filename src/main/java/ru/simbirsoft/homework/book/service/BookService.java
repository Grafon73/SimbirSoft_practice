package ru.simbirsoft.homework.book.service;

import ru.simbirsoft.homework.book.repository.Attribute;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;

import java.util.List;


public interface BookService {

    /**
     * Добавить книгу
     */
    BookView addBook(BookView bookView);

    /**
     * Удалить книгу
     */
    void removeBook(Integer id);

    /**
     * Изменить жанр
     */
    BookView editGenre(BookViewWithoutAuthor bookView);

    /**
     * Список книг по автору
     */
    List<BookView> getBooksByAuthor(String firstName, String lastName, String middleName);

    /**
     * Список книг по жанру
     */
    List<BookView> getBooksByGenre(String name);

    /**
     * Список книг по жанру и дате публикации
     */
    List<BookView> getBooksByGenreAndDate(String genre, Integer year, Attribute attribute);


}
