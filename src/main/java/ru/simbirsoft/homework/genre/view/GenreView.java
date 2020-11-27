package ru.simbirsoft.homework.genre.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * View-класс для Genre
 */
@Getter
@Setter
public class GenreView {

    /**
     * Название жанра
     */
    @NotBlank(message = "Жанр не может быть пустым")
    private String name;

}
