package ru.simbirsoft.homework.person.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * View-класс для Person без отображения книг
 */
@Getter
@Setter
public class PersonViewWithoutBooks {

    /**
     * Дата рождения
     */
    private LocalDate birthDate;

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
    private String middleName;

}
