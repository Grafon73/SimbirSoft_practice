package ru.simbirsoft.homework.person.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * View-класс для Person без отображения книг
 */
@Getter
@Setter
public class PersonViewWithoutDateAndBooks {

    /**
     * Имя
     */
    @NotNull(message = "Имя не может быть пустым")
    private String firstName;

    /**
     * Фамилия
     */
    @NotNull(message = "Фамилия не может быть пустым")
    private String lastName;

    /**
     * Отчество
     */
    @NotNull(message = "Отчество не может быть пустым")
    private String middleName;
}

