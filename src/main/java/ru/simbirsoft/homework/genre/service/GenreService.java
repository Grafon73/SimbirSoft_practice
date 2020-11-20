package ru.simbirsoft.homework.genre.service;

import ru.simbirsoft.homework.genre.view.GenreView;

import java.util.List;

/**
 * Service для работы с Genre
 */
public interface GenreService {

    /**
     * Получить все объекты Genre
     */
    List<GenreView> all();


    /**
     * Сохранить Genre
     */
    void add(GenreView genreView);

    /**
     * Получить количество книг по Genre
     */
    Integer stats(String name);

    /**
     * Удалить Genre
     */
    void remove(String name);
}
