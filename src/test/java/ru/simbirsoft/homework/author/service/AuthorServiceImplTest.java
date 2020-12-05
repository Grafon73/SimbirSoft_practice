package ru.simbirsoft.homework.author.service;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.simbirsoft.homework.author.model.AuthorEntity;
import ru.simbirsoft.homework.author.repository.AuthorRepo;
import ru.simbirsoft.homework.author.repository.CustomAuthorRepo;
import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.person.model.PersonEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class AuthorServiceImplTest {

    @Mock
    AuthorRepo authorRepo;
    @Mock
    MapperFactory mapperFactory;
    @Mock
    CustomAuthorRepo customAuthorRepo;
    @InjectMocks
    AuthorServiceImpl authorService;


    @Test
    void listOfBooksByAuthor_ReturnRightView() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setFirstName("Test");
        authorEntity.setLastName("Test");
        authorEntity.setBooks(new HashSet<BookEntity>());
        authorEntity.setBirthDate(new Date());
        Optional<AuthorEntity> authorEntityOptional = Optional.of(authorEntity);
        Mockito.when(authorRepo.findById(1)).thenReturn(authorEntityOptional);
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        assertThat(authorService.listOfBooksByAuthor(1)).isInstanceOf(AuthorView.class);
    }

    @Test
    void listOfBooksByAuthor_ReturnRightException() {
        Mockito.when(authorRepo.findById(1)).thenReturn(Optional.empty());
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        assertThrows(CustomRuntimeException.class, () -> authorService.listOfBooksByAuthor(1));
    }

    @Test
    void addAuthor_Success() {
        AuthorView authorView = new AuthorView(
                "Test",
                "Test",
                "Test",
                new Date(),
                new ArrayList<>()
        );
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(authorRepo.findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());
        assertThat(authorService.addAuthor(authorView)).isInstanceOf(AuthorView.class);
        Mockito.verify(authorRepo, Mockito.times(1))
                .findAuthorEntityByFirstNameAndLastNameAndMiddleName(
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString()
                );
        Mockito.verify(authorRepo, Mockito.times(1))
                .save(Mockito.any(AuthorEntity.class));
    }

    @Test
    void removeAuthor_AuthorNotFound() {
        Mockito.when(authorRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> authorService.removeAuthor(1));
        Mockito.verify(authorRepo, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    void removeAuthor_AuthorsBookBorrowed() {
        LibraryCard libraryCard = new LibraryCard();
        PersonEntity personEntity = new PersonEntity();
        libraryCard.setPerson(personEntity);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setPersons(Collections.singleton(libraryCard));
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setBooks(Collections.singleton(bookEntity));
        Optional<AuthorEntity> optionalAuthorEntity = Optional.of(authorEntity);
        Mockito.when(authorRepo.findById(Mockito.anyInt())).thenReturn(optionalAuthorEntity);
        assertThrows(CustomRuntimeException.class, () -> authorService.removeAuthor(1));
        Mockito.verify(authorRepo, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    void removeAuthor_Success() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setBooks(new HashSet<>());
        Optional<AuthorEntity> optionalAuthorEntity = Optional.of(authorEntity);
        Mockito.when(authorRepo.findById(Mockito.anyInt())).thenReturn(optionalAuthorEntity);
        authorService.removeAuthor(1);
        Mockito.verify(authorRepo, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(authorRepo, Mockito.times(1)).delete(authorEntity);
    }

    @Test
    void getAuthorsByBirthDateAndFIO() {
        Mockito.when(customAuthorRepo.findByFIOAndBirthDate(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(Collections.singletonList(new AuthorEntity()));
        DefaultMapperFactory myMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(myMapperFactory.getMapperFacade());
        List<AuthorWithoutBooks> authorEntityList = authorService.getAuthorsByBirthDateAndFIO(
                "Test",
                "Test",
                "Test",
                new Date(),
                new Date()
        );

        assertThat(authorEntityList).isNotEmpty();
        Mockito.verify(customAuthorRepo, Mockito.times(1)).findByFIOAndBirthDate(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(),
                Mockito.any()
        );
    }
}