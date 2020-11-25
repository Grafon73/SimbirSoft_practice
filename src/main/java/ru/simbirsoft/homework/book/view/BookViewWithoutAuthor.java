package ru.simbirsoft.homework.book.view;

import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.genre.view.GenreView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Название не может быть пустым")
    private String name;

    /**
     * Жанры книги
     */
    @Valid
    private Set<GenreView> genres;

}
