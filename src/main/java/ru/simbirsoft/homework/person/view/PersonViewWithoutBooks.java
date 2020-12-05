package ru.simbirsoft.homework.person.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * View-класс для Person без отображения книг
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonViewWithoutBooks {


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


    /**
     * Дата рождения
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
