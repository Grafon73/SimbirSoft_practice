package ru.simbirsoft.homework.book.service;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.author.repository.AuthorRepo;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.repository.CustomBookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.genre.repository.GenreRepo;
import ru.simbirsoft.homework.genre.view.GenreView;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.person.model.PersonEntity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class BookServiceImplTest {

    @Mock
    BookRepo bookRepo;
    @Mock
    MapperFactory mapperFactory;
    @Mock
    GenreRepo genreRepo;
    @Mock
    AuthorRepo authorRepo;
    @Mock
    CustomBookRepo customBookRepo;

    @InjectMocks
    BookServiceImpl bookService;


    @Test
    void addBook_Success() {
        BookView bookView = new BookView(
                "Test",
                new Date(),
                new HashSet<>(),
                new AuthorWithoutBooks()
        );
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());
        assertThat(bookService.addBook(bookView)).isInstanceOf(BookView.class);
        Mockito.verify(authorRepo, Mockito.times(1))
                .findAuthorEntityByFirstNameAndLastNameAndMiddleName(Mockito.any(),Mockito.any(),Mockito.any());
        Mockito.verify(bookRepo, Mockito.times(1))
                .save(Mockito.any(BookEntity.class));
    }

    @Test
    void removeBook_Success() {
        BookEntity bookEntity = new BookEntity();
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(bookRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(bookEntity));
        bookService.removeBook(1);
        Mockito.verify(bookRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .deleteById(Mockito.anyInt());
    }

    @Test
    void removeBook_BookIsBorrowed() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setPerson(new PersonEntity());
        BookEntity bookEntity = new BookEntity();
        bookEntity.setPersons(Collections.singleton(libraryCard));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(bookRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(bookEntity));
        assertThrows(CustomRuntimeException.class, ()-> bookService.removeBook(1));
        Mockito.verify(bookRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(0))
                .delete(Mockito.any(BookEntity.class));
    }

    @Test
    void removeBook_BookIsNotFound() {
        Mockito.when(bookRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, ()-> bookService.removeBook(1));
        Mockito.verify(bookRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(0))
                .delete(Mockito.any(BookEntity.class));
    }

    @Test
    void editGenre_Success() {
        BookViewWithoutAuthor bookViewWithoutAuthor = new BookViewWithoutAuthor();
        bookViewWithoutAuthor.setName("Test");
        bookViewWithoutAuthor.setGenres(Collections.singleton(new GenreView()));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(new BookEntity()));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        bookService.editGenre(bookViewWithoutAuthor);
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(genreRepo, Mockito.times(1))
                .removeAllByBooksIsNull();
        Mockito.verify(bookRepo, Mockito.times(1))
                .save(Mockito.any(BookEntity.class));
    }

    @Test
    void editGenre_BookNotFound() {
        BookViewWithoutAuthor bookViewWithoutAuthor = new BookViewWithoutAuthor();
        bookViewWithoutAuthor.setName("Test");
        bookViewWithoutAuthor.setGenres(Collections.singleton(new GenreView()));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        assertThrows(DataNotFoundException.class, ()-> bookService.editGenre(bookViewWithoutAuthor));
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(genreRepo, Mockito.times(0))
                .removeAllByBooksIsNull();
        Mockito.verify(bookRepo, Mockito.times(0))
                .save(Mockito.any(BookEntity.class));
    }

    @Test
    void getBooksByAuthor_Success() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setBooks(Collections.singleton(new BookEntity()));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(authorRepo.findAll(Mockito.any(Example.class))).thenReturn(Collections.singletonList(authorEntity));
        assertThat(bookService.getBooksByAuthor("Test","Test","Test"))
                .isInstanceOf(List.class);
        Mockito.verify(authorRepo, Mockito.times(1))
                .findAll(Mockito.any(Example.class));
    }
    @Test

    void getBooksByAuthor_AuthorNotFound() {
        Mockito.when(authorRepo.findAll(Mockito.any(Example.class))).thenReturn(Collections.EMPTY_LIST);
        assertThrows(DataNotFoundException.class, ()->
                bookService.getBooksByAuthor("Test","Test","Test"));
        Mockito.verify(authorRepo, Mockito.times(1))
                .findAll(Mockito.any(Example.class));
    }
}