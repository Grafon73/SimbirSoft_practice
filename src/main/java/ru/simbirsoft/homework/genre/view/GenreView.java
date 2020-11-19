package ru.simbirsoft.homework.genre.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * View-класс для Genre
 */
@Getter
@Setter
public class GenreView {

    /**
     * Название жанра
     */
    @NotNull(message = "Название не может быть пустым")
    private String name;

}
