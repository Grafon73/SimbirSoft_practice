package ru.simbirsoft.homework.author.view;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    /**
     * Список написанных книг
     */
    @JsonProperty("Книги")
    private List<BookViewWithoutAuthor> books;

}
