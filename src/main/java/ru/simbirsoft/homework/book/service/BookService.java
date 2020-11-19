package ru.simbirsoft.homework.book.service;

import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.genre.view.GenreView;

import java.util.List;


public interface BookService {
    BookView addBook(BookView bookView);
    void removeBook(Integer id);
    BookView editGenre(BookViewWithoutAuthor bookView);
    List<BookView> getBooks(AuthorWithoutBooks authorView);
    List<BookView> getBooks(GenreView genreView);
}
