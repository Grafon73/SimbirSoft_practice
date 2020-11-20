package ru.simbirsoft.homework.person.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.book.view.BookView;

import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Имя не может быть пустым")
    @JsonProperty("Имя")
    private String firstName;

    /**
     * Фамилия
     */
    @NotNull(message = "Фамилия не может быть пустым")
    @JsonProperty("Фамилия")
    private String lastName;

    /**
     * Отчество
     */
    @JsonProperty("Отчество")
    private String middleName;

    /**
     * Дата рождения
     */
    @JsonProperty("Дата рождения")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    /**
     * Список взятых книг
     */
    @JsonProperty("Книги")
    private List<BookView> books;
}
