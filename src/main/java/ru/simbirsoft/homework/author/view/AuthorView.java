package ru.simbirsoft.homework.author.view;

import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * View-класс для Person
 */
@Getter
@Setter
public class AuthorView {
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

    /**
     * Список написанных книг
     */
    private List<BookViewWithoutAuthor> books;

}
