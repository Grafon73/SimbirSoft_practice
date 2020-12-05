package ru.simbirsoft.homework.person.service;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.simbirsoft.homework.book.model.BookEntity;
import ru.simbirsoft.homework.book.repository.BookRepo;
import ru.simbirsoft.homework.book.view.BookView;
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.exception.DataNotFoundException;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.librarycard.repository.LibraryRepo;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.repository.PersonRepo;
import ru.simbirsoft.homework.person.view.PersonView;
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;
import ru.simbirsoft.homework.person.view.PersonViewWithoutDateAndBooks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class PersonServiceImplTest {

    @Mock
    PersonRepo personRepo;
    @Mock
    MapperFactory mapperFactory;
    @Mock
    BookRepo bookRepo;
    @Mock
    LibraryRepo libraryRepo;
    @InjectMocks
    PersonServiceImpl personService;

    @Test
    void getPersonsBooks_Success() {
        PersonEntity personEntity = new PersonEntity();
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setBook(new BookEntity());
        personEntity.setBooks(Collections.singleton(libraryCard));
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        List<BookView> bookViewList = personService.getPersonsBooks(1);
        assertThat(bookViewList).isNotEmpty();
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
    }

    @Test
    void getPersonsBooks_PersonNotFound() {
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> personService.getPersonsBooks(1));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
    }

    @Test
    void addPerson_Success() {
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        assertThat(personService.addPerson(new PersonViewWithoutBooks())).isInstanceOf(PersonViewWithoutBooks.class);
        Mockito.verify(personRepo, Mockito.times(1))
                .save(Mockito.any(PersonEntity.class));
    }

    @Test
    void editPerson_Success() {
        PersonViewWithoutBooks personViewWithoutBooks = new PersonViewWithoutBooks();
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(new PersonEntity()));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        assertThat(personService.editPerson(1, personViewWithoutBooks)).isInstanceOf(PersonViewWithoutBooks.class);
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(personRepo, Mockito.times(1))
                .save(Mockito.any(PersonEntity.class));
    }

    @Test
    void editPerson_PersonNotFound() {
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> personService.editPerson(1, new PersonViewWithoutBooks()));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(personRepo, Mockito.times(0))
                .save(Mockito.any(PersonEntity.class));
    }


    @Test
    void removePerson_Success() {
        PersonEntity personEntity = new PersonEntity();
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(true);
        personEntity.setBooks(Collections.singleton(libraryCard));
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        personService.removePerson(1);
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(personRepo, Mockito.times(1))
                .delete(Mockito.any(PersonEntity.class));
    }

    @Test
    void removePerson_PersonNotFound() {
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> personService.removePerson(1));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(personRepo, Mockito.times(0))
                .delete(Mockito.any(PersonEntity.class));
    }

    @Test
    void removePerson_PersonHaveNotBorrowedBooks() {
        PersonEntity personEntity = new PersonEntity();
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(false);
        personEntity.setBooks(Collections.singleton(libraryCard));
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        assertThrows(CustomRuntimeException.class, () -> personService.removePerson(1));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(personRepo, Mockito.times(0))
                .delete(Mockito.any(PersonEntity.class));
    }

    @Test
    void testRemovePerson_Success() {
        PersonViewWithoutDateAndBooks personView = new PersonViewWithoutDateAndBooks(
                "Test",
                "Test",
                "Test"
        );
        PersonEntity personEntity = new PersonEntity();
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(true);
        personEntity.setBooks(Collections.singleton(libraryCard));
        Mockito.when(personRepo.findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(Collections.singletonList(personEntity));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        personService.removePerson(personView);
        Mockito.verify(personRepo, Mockito.times(1))
                .findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString());
        Mockito.verify(personRepo, Mockito.times(1))
                .delete(Mockito.any(PersonEntity.class));
    }

    @Test
    void testRemovePerson_PersonNotFound() {
        PersonViewWithoutDateAndBooks personView = new PersonViewWithoutDateAndBooks(
                "Test",
                "Test",
                "Test"
        );
        Mockito.when(personRepo.findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(new ArrayList<>());
        assertThrows(DataNotFoundException.class, () -> personService.removePerson(personView));
        Mockito.verify(personRepo, Mockito.times(1))
                .findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString());
        Mockito.verify(personRepo, Mockito.times(0))
                .delete(Mockito.any(PersonEntity.class));
    }

    @Test
    void testRemovePerson_PersonHaveBorrowedBooks() {
        PersonViewWithoutDateAndBooks personView = new PersonViewWithoutDateAndBooks(
                "Test",
                "Test",
                "Test"
        );
        PersonEntity personEntity = new PersonEntity();
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(false);
        personEntity.setBooks(Collections.singleton(libraryCard));
        Mockito.when(personRepo.findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()))
                .thenReturn(Collections.singletonList(personEntity));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        assertThrows(CustomRuntimeException.class, () -> personService.removePerson(personView));
        Mockito.verify(personRepo, Mockito.times(1))
                .findAllPersonEntityByFirstNameAndLastNameAndMiddleName(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString());
        Mockito.verify(personRepo, Mockito.times(0))
                .delete(Mockito.any(PersonEntity.class));
    }
    @Test
    void borrowBook_Success() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(true);
        Set<LibraryCard> libraryCards = new HashSet<>();
        libraryCards.add(libraryCard);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setPersons(libraryCards);
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBooks(libraryCards);
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(bookEntity));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(mapperFactory.classMap(PersonEntity.class, PersonView.class))
                .thenReturn(customMapperFactory.classMap(PersonEntity.class, PersonView.class));
        assertThat(personService.borrowBook(1,"Test")).isInstanceOf(PersonView.class);
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(personRepo, Mockito.times(1))
                .save(Mockito.any(PersonEntity.class));
    }

    @Test
    void borrowBook_PersonNotFound() {
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, ()->personService.borrowBook(1,"Test"));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(0))
                .findByName(Mockito.anyString());
        Mockito.verify(personRepo, Mockito.times(0))
                .save(Mockito.any(PersonEntity.class));
    }

    @Test
    void borrowBook_BookNotFound() {
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(new PersonEntity()));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, ()->personService.borrowBook(1,"Test"));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(personRepo, Mockito.times(0))
                .save(Mockito.any(PersonEntity.class));
    }

    @Test
    void borrowBook_PersonHaveBorrowedBook() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(false);
        Set<LibraryCard> libraryCards = new HashSet<>();
        libraryCards.add(libraryCard);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setPersons(libraryCards);
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBooks(libraryCards);
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(bookEntity));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(mapperFactory.classMap(PersonEntity.class, PersonView.class))
                .thenReturn(customMapperFactory.classMap(PersonEntity.class, PersonView.class));
        assertThrows(CustomRuntimeException.class, ()->personService.borrowBook(1,"Test"));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(personRepo, Mockito.times(0))
                .save(Mockito.any(PersonEntity.class));
    }
    @Test
    void returnBook_Success() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(false);
        Set<LibraryCard> libraryCards = new HashSet<>();
        libraryCards.add(libraryCard);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("Test");
        libraryCard.setBook(bookEntity);
        bookEntity.setPersons(libraryCards);
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBooks(libraryCards);
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(bookEntity));
        Mockito.when(libraryRepo.findByPerson_PersonIdAndBook_NameAndInLibraryFalse(
                Mockito.anyInt(),
                Mockito.anyString()))
                .thenReturn(Optional.of(libraryCard));
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(mapperFactory.classMap(PersonEntity.class, PersonView.class))
                .thenReturn(customMapperFactory.classMap(PersonEntity.class, PersonView.class));
        assertThat(personService.returnBook(1,"Test")).isInstanceOf(PersonView.class);
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(1))
                .findByPerson_PersonIdAndBook_NameAndInLibraryFalse(Mockito.anyInt(),Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(1))
                .save(Mockito.any(LibraryCard.class));
    }

    @Test
    void returnBook_PersonNotFound() {
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, ()->personService.returnBook(1,"Test"));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(0))
                .findByName(Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .findByPerson_PersonIdAndBook_NameAndInLibraryFalse(Mockito.anyInt(),Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .save(Mockito.any(LibraryCard.class));
    }

    @Test
    void returnBook_BookNotFound() {
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(new PersonEntity()));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, ()->personService.returnBook(1,"Test"));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .findByPerson_PersonIdAndBook_NameAndInLibraryFalse(Mockito.anyInt(),Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .save(Mockito.any(LibraryCard.class));
    }

    @Test
    void returnBook_BookNotBelongToThisPerson() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(false);
        Set<LibraryCard> libraryCards = new HashSet<>();
        libraryCards.add(libraryCard);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("Test");
        libraryCard.setBook(bookEntity);
        bookEntity.setPersons(libraryCards);
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBooks(libraryCards);
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(bookEntity));
        Mockito.when(libraryRepo.findByPerson_PersonIdAndBook_NameAndInLibraryFalse(
                Mockito.anyInt(),
                Mockito.anyString()))
                .thenReturn(Optional.empty());
        assertThrows(CustomRuntimeException.class, ()->personService.returnBook(1,"Test"));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(1))
                .findByPerson_PersonIdAndBook_NameAndInLibraryFalse(Mockito.anyInt(),Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .save(Mockito.any(LibraryCard.class));
    }

    @Test
    void returnBook_BookAtTheLibrary() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setInLibrary(true);
        Set<LibraryCard> libraryCards = new HashSet<>();
        libraryCards.add(libraryCard);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("Test");
        libraryCard.setBook(bookEntity);
        bookEntity.setPersons(libraryCards);
        PersonEntity personEntity = new PersonEntity();
        personEntity.setBooks(libraryCards);
        Mockito.when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(personEntity));
        Mockito.when(bookRepo.findByName(Mockito.anyString())).thenReturn(Optional.of(bookEntity));
        assertThrows(CustomRuntimeException.class, ()->personService.returnBook(1,"Test"));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findByName(Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .findByPerson_PersonIdAndBook_NameAndInLibraryFalse(Mockito.anyInt(),Mockito.anyString());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .save(Mockito.any(LibraryCard.class));
    }
}