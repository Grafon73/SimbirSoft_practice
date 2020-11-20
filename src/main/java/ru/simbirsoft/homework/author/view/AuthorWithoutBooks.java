package ru.simbirsoft.homework.author.view;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("Имя")
    private String firstName;

    /**
     * Фамилия
     */
    @NotNull(message = "Фамилия не может быть пустая")
    @JsonProperty("Фамилия")
    private String lastName;

    /**
     * Отчество
     */
    @JsonProperty("Отчество")
    private String middleName;
}
