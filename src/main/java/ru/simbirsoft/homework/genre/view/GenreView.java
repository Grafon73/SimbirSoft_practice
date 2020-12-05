package ru.simbirsoft.homework.genre.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * View-класс для Genre
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreView {

    /**
     * Название жанра
     */
    @NotBlank(message = "Жанр не может быть пустым")
    private String name;

}
