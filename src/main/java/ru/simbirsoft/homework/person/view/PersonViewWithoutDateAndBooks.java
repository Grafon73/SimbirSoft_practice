package ru.simbirsoft.homework.person.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * View-класс для Person без отображения книг
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonViewWithoutDateAndBooks {

    /**
     * Имя
     */
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;

    /**
     * Фамилия
     */
    @NotBlank(message = "Фамилия не может быть пустым")
    private String lastName;

    /**
     * Отчество
     */
    private String middleName;
}

