package ru.simbirsoft.homework.librarycard.service;

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
import ru.simbirsoft.homework.exception.CustomRuntimeException;
import ru.simbirsoft.homework.librarycard.model.LibraryCard;
import ru.simbirsoft.homework.librarycard.repository.LibraryRepo;
import ru.simbirsoft.homework.librarycard.view.LibraryView;
import ru.simbirsoft.homework.person.model.PersonEntity;
import ru.simbirsoft.homework.person.repository.PersonRepo;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class LibraryServiceImplTest {

    @Mock
    LibraryRepo libraryRepo;
    @Mock
    MapperFactory mapperFactory;
    @Mock
    PersonRepo personRepo;
    @Mock
    BookRepo bookRepo;
    @InjectMocks
    LibraryServiceImpl libraryService;

    @Test
    void addDays_Success() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setReturned(LocalDateTime.now());
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.classMap(LibraryCard.class, LibraryView.class))
                .thenReturn(customMapperFactory.classMap(LibraryCard.class, LibraryView.class));
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(personRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new PersonEntity()));
        Mockito.when(bookRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new BookEntity()));
        Mockito.when(libraryRepo.findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Mockito.anyInt(),Mockito.anyInt()))
                .thenReturn(Optional.of(libraryCard));
        assertThat(libraryService.addDays(1,1,1)).isInstanceOf(LibraryView.class);
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(libraryRepo, Mockito.times(1))
                .save(Mockito.any(LibraryCard.class));
    }

    @Test
    void addDays_BookNotFound() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setReturned(LocalDateTime.now());
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.classMap(LibraryCard.class, LibraryView.class))
                .thenReturn(customMapperFactory.classMap(LibraryCard.class, LibraryView.class));
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(personRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new PersonEntity()));
        Mockito.when(bookRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        Mockito.when(libraryRepo.findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Mockito.anyInt(),Mockito.anyInt()))
                .thenReturn(Optional.of(libraryCard));
        assertThrows(CustomRuntimeException.class,()->libraryService.addDays(1,1,1));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Mockito.anyInt(),Mockito.anyInt());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .save(Mockito.any(LibraryCard.class));
    }

    @Test
    void addDays_PersonNotFound() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setReturned(LocalDateTime.now());
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.classMap(LibraryCard.class, LibraryView.class))
                .thenReturn(customMapperFactory.classMap(LibraryCard.class, LibraryView.class));
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(personRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        Mockito.when(bookRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new BookEntity()));
        Mockito.when(libraryRepo.findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Mockito.anyInt(),Mockito.anyInt()))
                .thenReturn(Optional.of(libraryCard));
        assertThrows(CustomRuntimeException.class,()->libraryService.addDays(1,1,1));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(0))
                .findById(Mockito.anyInt());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Mockito.anyInt(),Mockito.anyInt());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .save(Mockito.any(LibraryCard.class));
    }

    @Test
    void addDays_BookNotBorrowedByThisPerson() {
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setReturned(LocalDateTime.now());
        DefaultMapperFactory customMapperFactory = new DefaultMapperFactory.Builder().build();
        Mockito.when(mapperFactory.classMap(LibraryCard.class, LibraryView.class))
                .thenReturn(customMapperFactory.classMap(LibraryCard.class, LibraryView.class));
        Mockito.when(mapperFactory.getMapperFacade())
                .thenReturn(customMapperFactory.getMapperFacade());
        Mockito.when(personRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new PersonEntity()));
        Mockito.when(bookRepo.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new BookEntity()));
        Mockito.when(libraryRepo.findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Mockito.anyInt(),Mockito.anyInt()))
                .thenReturn(Optional.empty());
        assertThrows(CustomRuntimeException.class,()->libraryService.addDays(1,1,1));
        Mockito.verify(personRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(bookRepo, Mockito.times(1))
                .findById(Mockito.anyInt());
        Mockito.verify(libraryRepo, Mockito.times(1))
                .findByBook_BookIdAndPerson_PersonIdAndInLibraryFalse(Mockito.anyInt(),Mockito.anyInt());
        Mockito.verify(libraryRepo, Mockito.times(0))
                .save(Mockito.any(LibraryCard.class));
    }
}