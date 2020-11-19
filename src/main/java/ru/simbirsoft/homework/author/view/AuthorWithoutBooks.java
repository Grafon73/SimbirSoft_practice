package ru.simbirsoft.homework.author.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * View-класс для Person без отображения жанров и книг
 */
@Getter
@Setter
public class AuthorWithoutBooks {
    /**
     * Имя
     */
    @NotNull(message = "Имя не может быть пустым")
    private String firstName;

    /**
     * Фамилия
     */
    @NotNull(message = "Фамилия не может быть пустая")
    private String lastName;

    /**
     * Отчество
     */
    private String middleName;
}
