package ru.simbirsoft.homework.person.view;

import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.book.view.BookView;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * View-класс для Person
 */
@Getter
@Setter
public class PersonView {

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

    /**
     * Список взятых книг
     */
    private List<BookView> books;
}
