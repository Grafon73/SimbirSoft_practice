package ru.simbirsoft.homework.book.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.genre.view.GenreView;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

/**
 * View-класс для Person
 */
@Getter
@Setter
public class BookViewWithoutAuthor {

    /**
     * Название книги
     */
    @NotBlank(message = "Название не может быть пустым")
    private String name;

    /**
     * Дата публикации
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date publicated;

    /**
     * Жанры книги
     */
    @Valid
    private Set<GenreView> genres;

}
