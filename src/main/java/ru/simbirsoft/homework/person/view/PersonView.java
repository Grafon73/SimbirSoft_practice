package ru.simbirsoft.homework.person.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.book.view.BookView;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * View-класс для Person
 */
@Getter
@Setter
public class PersonView {

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

    /**
     * Список взятых книг
     */
    private List<BookView> books;
}
