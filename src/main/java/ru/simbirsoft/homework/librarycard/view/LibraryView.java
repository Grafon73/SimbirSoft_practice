package ru.simbirsoft.homework.librarycard.view;

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
    private PersonViewWithoutBooks personViewWithoutBooks;

    /**
     * Книга
     */
    private BookViewWithoutAuthor bookViewWithoutAuthor;

    /**
     * Время возврата
     */
    private String  expired;
}
