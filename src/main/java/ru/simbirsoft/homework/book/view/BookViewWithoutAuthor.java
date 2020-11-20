package ru.simbirsoft.homework.book.view;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("Название книги")
    private String name;

    /**
     * Жанры книги
     */
    @Valid
    @JsonProperty("Жанры")
    private Set<GenreView> genres;

}
