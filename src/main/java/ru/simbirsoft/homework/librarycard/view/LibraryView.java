package ru.simbirsoft.homework.librarycard.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;

@Getter
@Setter
public class LibraryView {
    /**
     * Читатель
     */
    @JsonProperty("Читатель")
    private PersonViewWithoutBooks personViewWithoutBooks;

    /**
     * Книга
     */
    @JsonProperty("Книга")
    private BookViewWithoutAuthor bookViewWithoutAuthor;

    /**
     * Время возврата
     */
    @JsonProperty("Время возврата")
    private String  expired;
}
