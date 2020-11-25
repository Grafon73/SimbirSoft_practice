package ru.simbirsoft.homework.author.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
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

    /**
     * Список написанных книг
     */
    private List<BookViewWithoutAuthor> books;


}
