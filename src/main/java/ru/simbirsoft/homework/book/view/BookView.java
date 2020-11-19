package ru.simbirsoft.homework.book.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.genre.view.GenreView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Название не может быть пустым")
    private String name;

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
