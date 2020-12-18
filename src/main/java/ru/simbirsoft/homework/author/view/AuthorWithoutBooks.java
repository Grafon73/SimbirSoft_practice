package ru.simbirsoft.homework.author.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * View-класс для Person без отображения жанров и книг
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorWithoutBooks {
    /**
     * Имя
     */
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;

    /**
     * Фамилия
     */
    @NotBlank(message = "Фамилия не может быть пустая")
    private String lastName;

    /**
     * Отчество
     */
    private String middleName;

    /**
     * Дата рождения
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
}
