package ru.simbirsoft.homework.book.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
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
public class BookView {

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
    @JsonIgnoreProperties({"bookList","author","genres"})
    private Set<GenreView> genres;

    /**
     * Автор книги
     */
    @Valid
    @JsonIgnoreProperties({"genres","author","bookList"})
    private AuthorWithoutBooks author;
}
